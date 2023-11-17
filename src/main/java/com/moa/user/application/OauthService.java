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
	 * OAuth 회원가입 및 이메일로 회사 인증
	 *
	 * @param oauthSignUpDto
	 * @return UserSignUpResultDto 회원가입 결과 정보
	 */
	UserSignUpResultDto createOauthLogin(OauthSignUpDto oauthSignUpDto);

	/**
	 * OAuth 회원가입 및 재직증명서로 회사 인증
	 *
	 * @param oauthSignUpDto
	 * @return
	 */
	UserSignUpResultDto createOauthLoginCertificate(OauthSignUpDto oauthSignUpDto);

}
