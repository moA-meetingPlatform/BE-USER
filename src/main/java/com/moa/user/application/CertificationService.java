package com.moa.user.application;


import com.moa.user.dto.CertificationCompanyEmailDto;


/**
 * 외부 플랫폼 등을 이용한 인증 서비스 (이메일 인증, SMS 인증 등)
 */

public interface CertificationService {

	/**
	 * 이메일 인증 코드를 생성하고 이메일을 전송한다.
	 *
	 * @param email
	 */
	void sendEmail(String email);

	/**
	 * 이메일 인증 코드를 검증하고 유저의 회사를 인증한다.
	 *
	 * @param dto 이메일, 인증 코드
	 */
	void confirmEmailAndCertificationCompany(CertificationCompanyEmailDto dto);

}
