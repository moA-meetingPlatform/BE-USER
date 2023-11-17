package com.moa.user.dto;


import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import com.moa.user.domain.Gender;
import com.moa.user.domain.OauthProviderType;
import com.moa.user.vo.request.AgreeAdvertiseRequest;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class OauthSignUpDto {

	private OauthProviderType oauthProvider;

	private String oauthUserId;

	private String name;

	private LocalDate birthdate;

	private Gender gender;

	private String phoneNumber;

	private String nickname;

	private AgreeAdvertiseRequest agreeAdvertiseRequest;

	private SignUpVerifyCompanyEmailDto signUpVerifyCompanyEmailDto;

	private CreateUserCompanyCertificateDto createUserCompanyCertificateDto;


	public void setSignUpVerifyCompanyEmailDto(SignUpVerifyCompanyEmailDto signUpVerifyCompanyEmailDto) {
		this.signUpVerifyCompanyEmailDto = signUpVerifyCompanyEmailDto;
	}


	public void setCreateUserCompanyCertificateDto(CreateUserCompanyCertificateDto createUserCompanyCertificateDto) {
		this.createUserCompanyCertificateDto = createUserCompanyCertificateDto;
	}

}
