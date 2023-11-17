package com.moa.user.vo.request;


import lombok.Getter;


@Getter
public class SignUpVerifyCompanyEmailRequest {
	private Integer companyId;
	private String companyEmail;
}
