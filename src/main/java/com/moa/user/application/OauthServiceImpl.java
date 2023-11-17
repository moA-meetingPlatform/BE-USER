package com.moa.user.application;


import com.moa.certificate.application.UserCompanyCertificateService;
import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import com.moa.company.application.CompanyService;
import com.moa.company.domain.CompanyCategory;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.domain.OauthLogin;
import com.moa.user.domain.User;
import com.moa.user.domain.UserScore;
import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.OauthLoginDto;
import com.moa.user.dto.OauthSignUpDto;
import com.moa.user.dto.UserSignUpResultDto;
import com.moa.user.infrastructure.OauthLoginRepository;
import com.moa.user.infrastructure.UserRepository;
import com.moa.user.infrastructure.UserScoreRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final ModelMapper modelMapper;

	private final OauthLoginRepository oauthLoginRepository;
	private final CompanyService companyService;
	private final UserCompanyCertificateService userCompanyCertificateService;

	private final UserRepository userRepository;
	private final UserScoreRepository userScoreRepository;


	@Override
	public LoginResultInfoDto oauthLogin(OauthLoginDto oauthLoginDto) {

		// oauth id, provider로 user 조회
		OauthLogin oauthLogin = oauthLoginRepository.findByOauthProviderAndOauthUserId(oauthLoginDto.getOauthProvider(), oauthLoginDto.getOauthUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

		User user = oauthLogin.getUser();

		// jwt token 생성 후 LoginInfoDto에 담아서 리턴
		String jwtToken = jwtTokenProvider.generateToken(user);
		LoginResultInfoDto loginResultInfoDto = modelMapper.map(user, LoginResultInfoDto.class);
		loginResultInfoDto.setToken(jwtToken);

		// user의 생년월일로부터 년도 추출
		loginResultInfoDto.setAgeFromBirthdate(user.getBirthdate());

		// user의 회사 id로 회사 카테고리 조회
		CompanyCategory companyCategory;
		if (user.getCompanyId() == null) {
			companyCategory = CompanyCategory.SMALL_COMPANY;
		} else {
			companyCategory = companyService.getCompanyCategoryById(user.getCompanyId());
		}
		loginResultInfoDto.setCompanyCategory(companyCategory);
		return loginResultInfoDto;
	}


	@Override
	public UserSignUpResultDto createOauthLogin(OauthSignUpDto oauthSignUpDto) {
		if (oauthLoginRepository.existsByOauthProviderAndOauthUserId(oauthSignUpDto.getOauthProvider(), oauthSignUpDto.getOauthUserId())) {
			throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
		}

		UUID uuid = UUID.randomUUID();

		User user = User.builder()
			.userUuid(uuid)
			.name(oauthSignUpDto.getName())
			.birthdate(oauthSignUpDto.getBirthdate())
			.gender(oauthSignUpDto.getGender())
			.phoneNumber(oauthSignUpDto.getPhoneNumber())
			.nickname(oauthSignUpDto.getNickname())
			.emailNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isEmailNotificationStatus())
			.smsNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isSmsNotificationStatus())
			.pushNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isPushNotificationStatus())
			.userSoftDelete(false)
			.companyCertificationStatus(true)
			.companyId(oauthSignUpDto.getSignUpVerifyCompanyEmailDto().getCompanyId())
			.build();

		// user 테이블에 저장
		userRepository.save(user);

		// userScore 테이블에도 저장
		userScoreRepository.save(new UserScore(user));

		// oauthLogin 테이블에 저장
		oauthLoginRepository.save(new OauthLogin(oauthSignUpDto.getOauthProvider(), oauthSignUpDto.getOauthUserId(), user));

		return UserSignUpResultDto.builder()
			.useruuid(uuid)
			.build();
	}


	//	@Override
	public UserSignUpResultDto createOauthLoginCertificate(OauthSignUpDto oauthSignUpDto) {
		if (oauthLoginRepository.existsByOauthProviderAndOauthUserId(oauthSignUpDto.getOauthProvider(), oauthSignUpDto.getOauthUserId())) {
			throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
		}

		UUID uuid = UUID.randomUUID();

		User user = User.builder()
			.userUuid(uuid)
			.name(oauthSignUpDto.getName())
			.birthdate(oauthSignUpDto.getBirthdate())
			.gender(oauthSignUpDto.getGender())
			.phoneNumber(oauthSignUpDto.getPhoneNumber())
			.nickname(oauthSignUpDto.getNickname())
			.emailNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isEmailNotificationStatus())
			.smsNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isSmsNotificationStatus())
			.pushNotificationStatus(oauthSignUpDto.getAgreeAdvertiseRequest().isPushNotificationStatus())
			.userSoftDelete(false)
			.companyCertificationStatus(false)
			.build();

		// user 테이블에 저장
		userRepository.save(user);

		// userScore 테이블에도 저장
		userScoreRepository.save(new UserScore(user));

		// oauthLogin 테이블에 저장
		oauthLoginRepository.save(new OauthLogin(oauthSignUpDto.getOauthProvider(), oauthSignUpDto.getOauthUserId(), user));

		CreateUserCompanyCertificateDto createUserCompanyCertificateDto = oauthSignUpDto.getCreateUserCompanyCertificateDto();
		createUserCompanyCertificateDto.setUserId(user.getId());
		userCompanyCertificateService.createUserCompanyCertificate(oauthSignUpDto.getCreateUserCompanyCertificateDto());

		return UserSignUpResultDto.builder()
			.useruuid(uuid)
			.build();
	}

}
