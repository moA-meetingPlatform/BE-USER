package com.moa.user.application;


import com.moa.company.application.CompanyService;
import com.moa.company.domain.CompanyCategory;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.domain.User;
import com.moa.user.domain.UserScore;
import com.moa.user.dto.LoginDto;
import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.UserSignUpDto;
import com.moa.user.dto.UserSignUpResultDto;
import com.moa.user.infrastructure.UserRepository;
import com.moa.user.infrastructure.UserScoreRepository;
import com.moa.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

	private final ModelMapper modelMapper;  // modelMapper 주입
	private final JwtTokenProvider jwtTokenProvider;

	private final UserRepository userRepository;
	private final UserScoreRepository userScoreRepository;
	private final UserService userService;

	private final CompanyService companyService;


	/**
	 * 로그인
	 *
	 * @param loginDto 로그인 아이디, 비밀번호
	 * @return LoginInfoDto jwt token 및 유저 정보
	 */
	@Override
	@Transactional(readOnly = true)
	public LoginResultInfoDto login(LoginDto loginDto) {

		// loginId로 user 조회
		User user = userRepository.findByLoginId(loginDto.getLoginId())
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

		// password 일치 확인
		//		authenticationManager.authenticate(
		//			new UsernamePasswordAuthenticationToken(
		//				user.getUsername(),
		//				loginDto.getPassword()
		//			)
		//		);
		if (!new BCryptPasswordEncoder().matches(loginDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.FAIL_LOGIN);
		}

		// jwt token 생성 후 LoginInfoDto에 담아서 리턴
		String jwtToken = jwtTokenProvider.generateToken(user);
		LoginResultInfoDto loginResultInfoDto = modelMapper.map(user, LoginResultInfoDto.class);
		loginResultInfoDto.setToken(jwtToken);

		// user의 생년월일로부터 년도 추출
		loginResultInfoDto.setAgeFromBirthdate(user.getBirthdate());

		// user의 회사 id로 회사 카테고리 조회
		CompanyCategory companyCategory = null;
		if (user.getCompanyId() == null) {
			companyCategory = CompanyCategory.SMALL_COMPANY;
		} else {
			companyCategory = companyService.getCompanyCategoryById(user.getCompanyId());
		}
		loginResultInfoDto.setCompanyCategory(companyCategory);
		return loginResultInfoDto;
	}


	@Override
	@Transactional
	public UserSignUpResultDto signUp(UserSignUpDto userSignUpDto) {
		userService.checkCanUseLoginId(userSignUpDto.getLoginId());

		UUID uuid = UUID.randomUUID();
		String hashedPassword = new BCryptPasswordEncoder().encode(userSignUpDto.getPassword());

		User user = User.builder()
			.userUuid(uuid)
			.loginId(userSignUpDto.getLoginId())
			.password(hashedPassword)
			.name(userSignUpDto.getName())
			.birthdate(userSignUpDto.getBirthdate())
			.gender(userSignUpDto.getGender())
			.phoneNumber(userSignUpDto.getPhoneNumber())
			.nickname(userSignUpDto.getNickname())
			.emailNotificationStatus(userSignUpDto.getAgreeAdvertiseRequest().isEmailNotificationStatus())
			.smsNotificationStatus(userSignUpDto.getAgreeAdvertiseRequest().isSmsNotificationStatus())
			.pushNotificationStatus(userSignUpDto.getAgreeAdvertiseRequest().isPushNotificationStatus())
			.userSoftDelete(false)
			.companyCertificationStatus(false)
			.build();

		// user 테이블에 저장
		userRepository.save(user);

		// userScore 테이블에도 저장
		userScoreRepository.save(new UserScore(user));

		return UserSignUpResultDto.builder()
			.useruuid(uuid)
			.build();
	}


	@Override
	public FindIDOut findID(String userName, String phone) {
		return null;
	}


	@Override
	public void changePassword(String token, String oldPwd, String newPwd) {

	}


	@Override
	public void searchPassword(String loginId, String newPwd) {

	}


	@Override
	public void withdrawal(String token) {

	}


	@Override
	public void authenticatePassword(String token, AuthenticatePasswordIn authenticatePasswordIn) {

	}


	@Override
	public CheckUserOut getOtherUserInfo(CheckUserIn checkUserIn) {
		return null;
	}


	@Override
	public LoginDto findOauth(OauthIn oauthIn) {
		return null;
	}


	@Override
	public void createOauth(String token, OauthIn oauthIn) {

	}

}
