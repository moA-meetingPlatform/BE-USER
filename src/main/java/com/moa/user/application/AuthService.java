package com.moa.user.application;


import com.moa.user.dto.LoginDto;
import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.UserSignUpDto;
import com.moa.user.dto.UserSignUpResultDto;
import com.moa.user.vo.AuthenticatePasswordIn;
import com.moa.user.vo.CheckUserIn;
import com.moa.user.vo.CheckUserOut;
import com.moa.user.vo.FindIDOut;
import com.moa.user.vo.request.OauthLoginRequest;


/**
 * AuthService 인터페이스는 회원가입, 로그인과 관련된 여러 작업을 위한 메서드 선언을 포함합니다.
 */
//
public interface AuthService {

	LoginResultInfoDto login(LoginDto loginDto);

	// 사용자를 생성하고 생성된 사용자의 ID를 반환합니다.
	UserSignUpResultDto signUp(UserSignUpDto userSignUpDto);

	// --------------------------------------------------------------

	// 사용자 이름과 전화번호를 통해 ID를 찾습니다.
	FindIDOut findID(String userName, String phone);

	// 토큰과 예전 비밀번호, 새로운 비밀번호를 사용하여 비밀번호를 변경합니다.
	void changePassword(String token, String oldPwd, String newPwd);

	// 로그인 ID를 통해 비밀번호를 검색하고 새로운 비밀번호로 설정합니다.
	void searchPassword(String loginId, String newPwd);

	// 토큰을 통해 사용자 계정을 탈퇴합니다.
	void withdrawal(String token);

	// 토큰과 인증 정보를 사용하여 비밀번호를 인증합니다.
	void authenticatePassword(String token, AuthenticatePasswordIn authenticatePasswordIn);

	// 사용자 정보를 확인합니다.
	CheckUserOut getOtherUserInfo(CheckUserIn checkUserIn);

	// 토큰과 OAuth 정보를 사용하여 OAuth를 생성합니다.
	void createOauth(String token, OauthLoginRequest oauthLoginRequest);

}
