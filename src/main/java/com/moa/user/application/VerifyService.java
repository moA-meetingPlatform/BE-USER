package com.moa.user.application;


import com.moa.user.dto.VerifyEmailDto;


/**
 * 외부 플랫폼 등을 이용한 인증 서비스 (이메일 인증, SMS 인증 등)
 */

public interface VerifyService {

	/**
	 * 이메일 인증 코드를 생성하고 이메일을 전송한다.
	 *
	 * @param email
	 */
	void sendEmail(String email);

	/**
	 * 이메일 인증 코드를 검증한다.
	 *
	 * @param dto 이메일, 인증 코드
	 */
	void verifyEmailByCode(VerifyEmailDto dto);

}
