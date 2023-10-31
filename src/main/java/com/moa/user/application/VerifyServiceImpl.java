package com.moa.user.application;


import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.domain.redis.EmailVerifyCode;
import com.moa.user.dto.VerifyEmailDto;
import com.moa.user.infrastructure.redis.EmailVerifyCodeRedisRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class VerifyServiceImpl implements VerifyService {

	private final JavaMailSender sender;
	private final EmailVerifyCodeRedisRepository emailVerifyCodeRedisRepository;

	private final String MAIL_SUBJECT = "[MOA] 이메일 인증 코드";

	@Value("${spring.mail.username}")
	private String configEmail;


	public void sendEmail(String email) {
		String code = RandomStringUtils.randomAlphanumeric(6); // 코드 생성 (6자리 영어 + 숫자)
		log.debug("code: {}", code);

		// Redis에 이메일 인증 코드 저장
		emailVerifyCodeRedisRepository.save(
			EmailVerifyCode.builder()
				.email(email)
				.code(code)
				.build()
		);

		// 인증코드를 포함한 이메일 전송
		try {
			sendCodeEmail(email, code);
		} catch (MessagingException e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}


	public void verifyEmailByCode(VerifyEmailDto verifyEmailDto) {
		String code = verifyEmailDto.getCode();
		String email = verifyEmailDto.getEmail();

		// redis에 저장된 인증번호 확인
		EmailVerifyCode emailVerifyCode = emailVerifyCodeRedisRepository.findById(email)
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_CERT_CODE));   // 인증번호가 만료되었거나 존재하지 않는 경우
		if (!emailVerifyCode.getCode().equals(code)) throw new CustomException(ErrorCode.INVALID_CERT_CODE);  // 인증번호가 일치하지 않는 경우

		// 확인 후 redis에 저장된 인증번호 삭제
		emailVerifyCodeRedisRepository.delete(emailVerifyCode);
	}


	public void sendCodeEmail(String email, String code) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		message.addRecipients(Message.RecipientType.TO, email);    // 수신자 설정
		message.setSubject(MAIL_SUBJECT); // 메일 제목 설정

		message.setText(makeEmailContentWithCode(code), "utf-8", "html"); // 메일 내용 설정
		message.setFrom(configEmail);
		sender.send(message); // 메일 전송
	}


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
