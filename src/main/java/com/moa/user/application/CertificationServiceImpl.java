package com.moa.user.application;


import com.moa.company.application.CompanyService;
import com.moa.company.dto.CompanySimpleInfoDto;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.domain.redis.CertificationCompanyEmail;
import com.moa.user.dto.CompanyCertificationDto;
import com.moa.user.dto.ConfirmCompanyEmailDto;
import com.moa.user.infrastructure.redis.CompanyEmailCertificateRedisRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class CertificationServiceImpl implements CertificationService {

	private final static String MAIL_SUBJECT = "[MOA] 이메일 인증 코드";
	private final ModelMapper modelMapper;

	private final JavaMailSender sender;
	private final CompanyEmailCertificateRedisRepository companyEmailCertificateRedisRepository;

	private final CompanyService companyService;
	private final UserService userService;

	@Value("${spring.mail.username}")
	private String configEmail;


	/**
	 * 회사 이메일 인증 요청
	 * - 회사 이메일 도메인을 추출하여 회사 id 조회
	 * - 인증코드 생성
	 * - Redis에 이메일 인증 코드 및 회사 id 저장
	 * - 인증코드를 포함한 이메일 전송
	 *
	 * @param email
	 */
	public void sendEmail(String email) {
		/* 이메일 도메인 추출 */
		String companyEmailDomain = email.split("@")[1];

		/*
		 회사 도메인인지 확인, 회사 도메인이면 companyId 리턴됨
		 - 회사 도메인이 아니면 CustomException(NOT_FOUND_RESOURCE) 발생
		 */
		int companyId = companyService.getCompanyIdByCompanyEmail(companyEmailDomain);

		String code = RandomStringUtils.randomAlphanumeric(6); // 코드 생성 (6자리 영어 + 숫자)
		log.debug("code: {}", code);

		/* Redis에 이메일 인증 코드 및 회사 id 저장 */
		companyEmailCertificateRedisRepository.save(
			CertificationCompanyEmail.builder()
				.companyEmail(email)
				.code(code)
				.companyId(companyId)
				.build()
		);

		/* 인증코드를 포함한 이메일 전송 */
		try {
			sendCodeEmail(email, code);
		} catch (MessagingException e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * 이메일 인증번호 확인
	 * - redis에 저장된 인증정보와 인증번호 비교
	 *
	 * @param confirmCompanyEmailDto 이메일, 인증 코드
	 */
	public CompanySimpleInfoDto confirmEmailAndUpdateCertificationCompany(ConfirmCompanyEmailDto confirmCompanyEmailDto) {
		String code = confirmCompanyEmailDto.getCode();

		// redis에 저장된 인증정보 확인 (인증번호를 전송한 회사 이메일을 ID로 인증번호, 회사id 조회)
		CertificationCompanyEmail certificationCompanyEmail = companyEmailCertificateRedisRepository.findById(confirmCompanyEmailDto.getCompanyEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_CERT_CODE));   // 인증번호가 만료되었거나 존재하지 않는 경우
		if (!certificationCompanyEmail.getCode().equals(code)) throw new CustomException(ErrorCode.INVALID_CERT_CODE);  // 인증번호가 일치하지 않는 경우

		log.debug("emailVerifyCode - companyId: {}", certificationCompanyEmail.getCompanyId());
		log.debug("emailVerifyCode - code: {}", certificationCompanyEmail.getCode());
		log.debug("emailVerifyCode - uuid: {}", confirmCompanyEmailDto.getUserUuid());

		if (confirmCompanyEmailDto.getUserUuid() != null) {
			log.info("회사 인증 완료");
			// 회사 인증 정보 업데이트
			userService.modifyCompanyCertification(
				new CompanyCertificationDto(confirmCompanyEmailDto.getUserUuid(), certificationCompanyEmail)
			);
		}

		// 확인 후 redis에 저장된 인증정보 삭제
		companyEmailCertificateRedisRepository.delete(certificationCompanyEmail);

		// 회사 정보 조회
		return companyService.getCompanySimpleInfoById(certificationCompanyEmail.getCompanyId());
	}


	/**
	 * 인증코드를 포함한 이메일 전송
	 *
	 * @param email 수신자 이메일
	 * @param code  인증코드
	 * @throws MessagingException 메일 전송 실패
	 */
	private void sendCodeEmail(String email, String code) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		message.addRecipients(Message.RecipientType.TO, email);    // 수신자 설정
		message.setSubject(MAIL_SUBJECT); // 메일 제목 설정

		message.setText(makeEmailContentWithCode(code), "utf-8", "html"); // 메일 내용 설정
		message.setFrom(configEmail);
		sender.send(message); // 메일 전송
	}


	/**
	 * 인증코드를 포함한 이메일 내용 생성
	 *
	 * @param code 인증코드
	 * @return 이메일 내용
	 */
	private String makeEmailContentWithCode(String code) {
		String contentStr =
			"""
			<div style="font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
				<h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
				<span style="font-size: 15px; margin: 0 0 10px 3px;">Moa</span><br />
				<span style="color: blue;">메일인증</span> 안내입니다.
				</h1>
				<p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
					안녕하세요.<br />
					Moa에 가입해 주셔서 진심으로 감사드립니다.<br />
					아래 <b>'메일 인증 번호'</b>를 입력하여 메일인증을 완료해 주세요.<br />
					감사합니다.<br/><br/>
				</p>
						
			"""
				+
				"""
					<div style='margin:50px;'>
						<div align='center' style='border:1px solid black; font-family:verdana';>
							<h3 style='color:blue;'>이메일 인증 코드</h3>
						    <div style='font-size:130%'>
						    <strong>
				"""
				+ code +
				"""
							</strong>
							</div><br/>
						</div>
					</div>
				"""
				+
				""" 
				</b>
				<br/><br/><br/></p>
				<div style="border-top: 1px solid #DDD; padding: 5px;">
				<p style="font-size: 13px; line-height: 21px; color: #555;">
				만약 인증번호가 정상적으로 보이지않거나 인증이 지속적으로 실패된다면 고객센터로 연락주시면 감사하겠습니다.<br />
				</p>
				</div>
				</div>
				""";
		return contentStr;
	}

}
