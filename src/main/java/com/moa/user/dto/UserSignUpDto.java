package com.moa.user.dto;


import com.moa.user.domain.Gender;
import com.moa.user.vo.request.AgreeAdvertiseRequest;
import com.moa.user.vo.request.SignUpVerifyCompanyCertificateRequest;
import com.moa.user.vo.request.SignUpVerifyCompanyEmailRequest;
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
	private SignUpVerifyCompanyEmailRequest signUpVerifyCompanyEmailRequest;
	private SignUpVerifyCompanyCertificateRequest signUpVerifyCompanyCertificateRequest;

}
