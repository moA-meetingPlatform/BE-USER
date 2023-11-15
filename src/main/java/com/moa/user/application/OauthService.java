package com.moa.user.application;


import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.OauthLoginDto;
import com.moa.user.dto.OauthSignUpDto;
import com.moa.user.dto.UserSignUpResultDto;


public interface OauthService {

	/**
	 * OAuth 로그인
	 *
	 * @param oauthLoginDto
	 * @return LoginInfoDto 로그인 결과 정보
	 */
	LoginResultInfoDto oauthLogin(OauthLoginDto oauthLoginDto);

	/**
	 * OAuth 회원가입
	 * 외부 로그인 정보로 회원가입을 진행합니다.
	 *
	 * @param oauthSignUpDto
	 * @return UserSignUpResultDto 회원가입 결과 정보
	 */
	UserSignUpResultDto createOauthLogin(OauthSignUpDto oauthSignUpDto);

}
