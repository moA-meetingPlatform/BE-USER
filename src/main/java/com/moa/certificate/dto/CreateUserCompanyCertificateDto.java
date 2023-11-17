package com.moa.certificate.dto;


import lombok.Getter;


@Getter
public class CreateUserCompanyCertificateDto {

	private Long userId;
	private String companyName;
	private String certificateImageUrl;


	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
