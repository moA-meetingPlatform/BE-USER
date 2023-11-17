package com.moa.user.vo.request;


import lombok.Getter;


@Getter
public class SignUpVerifyCompanyCertificateRequest {
	private Integer companyName;
	private String certificateImageUrl;
}
