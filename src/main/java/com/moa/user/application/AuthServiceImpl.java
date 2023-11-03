package com.moa.user.application;


import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import com.moa.user.config.security.JwtTokenProvider;
import com.moa.user.domain.User;
import com.moa.user.dto.*;
import com.moa.user.infrastructure.UserRepository;
import com.moa.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

	private final ModelMapper modelMapper;  // modelMapper 주입

	// security 관련 객체 주입
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	private final UserRepository userRepository;
	private final UserService userService;


	/**
	 * 로그인
	 *
	 * @param loginDto 로그인 아이디, 비밀번호
	 * @return LoginInfoDto jwt token 및 유저 정보
	 */
	@Override
	@Transactional(readOnly = true)
	public LoginInfoDto login(LoginDto loginDto) {

		// loginId로 user 조회
		User user = userRepository.findByLoginId(loginDto.getLoginId())
			.orElseThrow(() -> new CustomException(ErrorCode.FAIL_LOGIN));

		// password 일치 확인
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				user.getUsername(),
				loginDto.getPassword()
			)
		);

		// jwt token 생성 후 LoginInfoDto에 담아서 리턴
		String jwtToken = jwtTokenProvider.generateToken(user);
		LoginInfoDto loginInfoDto = modelMapper.map(user, LoginInfoDto.class);
		loginInfoDto.setToken(jwtToken);
		return loginInfoDto;
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
			.birthDate(userSignUpDto.getBirthDate())
			.gender(userSignUpDto.getGender())
			.phoneNumber(userSignUpDto.getPhoneNumber())
			.nickname(userSignUpDto.getNickname())
			.userSoftDelete(false)
			.userIntroduce(userSignUpDto.getIntroduction())
			.build();
		userRepository.save(user);
		return UserSignUpResultDto.builder()
			.useruuid(uuid)
			.build();
	}


	@Override
	public UserGetDto getUserByLoginId(String loginId) {
		return null;
	}


	@Override
	public UserGetDto getUserByUUID(String UUID) {
		return null;
	}


	@Override
	public List<User> getAllUsers() {
		return null;
	}


	@Override
	public void modify(String UUID, UserModifyIn userModifyIn) {

	}


	@Override
	public LoginDto loginUser(LoginRequest loginRequest) {
		return null;
	}


	@Override
	public UserGetDto getUserDtoFromToken(String token) {
		return null;
	}


	@Override
	public Long getUserId(String loginId) {
		return null;
	}


	@Override
	public Long getUserIdFromToken(String token) {
		return null;
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
	public User getUserFromToken(String token) {
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
