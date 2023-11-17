package com.moa.user.dto;


import lombok.Getter;

import java.util.UUID;


@Getter
public class ConfirmCompanyEmailDto {

	private UUID userUuid;
	private String companyEmail;
	private String code;


	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

}
