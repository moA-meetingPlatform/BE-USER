package com.moa.user.dto;


import lombok.Getter;

import java.util.UUID;


@Getter
public class CertificationCompanyEmailDto {

	private UUID userUuid;
	private String companyEmail;
	private String code;

}
