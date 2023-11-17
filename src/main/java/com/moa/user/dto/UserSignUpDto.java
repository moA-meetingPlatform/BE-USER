package com.moa.user.dto;


import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import com.moa.user.domain.Gender;
import com.moa.user.vo.request.AgreeAdvertiseRequest;
import lombok.*;

import java.time.LocalDate;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {

	private String loginId;
	private String password;
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
