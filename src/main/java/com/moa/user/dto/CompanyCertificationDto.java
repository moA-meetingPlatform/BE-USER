package com.moa.user.dto;


import com.moa.user.domain.redis.CertificationCompanyEmail;
import lombok.Getter;

import java.util.UUID;


@Getter
public class CompanyCertificationDto {

	private UUID userUuid;
	private Integer companyId;
	private String companyEmail;


	public CompanyCertificationDto(UUID userUuid, CertificationCompanyEmail certificationCompanyEmail) {
		this.userUuid = userUuid;
		this.companyId = certificationCompanyEmail.getCompanyId();
		this.companyEmail = certificationCompanyEmail.getCompanyEmail();
	}

}
