package com.moa.user.application;


import com.moa.user.dto.LoginResultInfoDto;
import com.moa.user.dto.OauthLoginDto;


public interface OauthService {

	/**
	 * OAuth 로그인
	 *
	 * @param oauthLoginDto
	 * @return LoginInfoDto 로그인 결과 정보
	 */
	LoginResultInfoDto oauthLogin(OauthLoginDto oauthLoginDto);

}
