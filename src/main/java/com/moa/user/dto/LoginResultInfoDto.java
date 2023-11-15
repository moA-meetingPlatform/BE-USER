package com.moa.user.dto;


import com.moa.company.domain.CompanyCategory;
import com.moa.user.domain.Gender;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;


@Getter
public class LoginResultInfoDto {

	private UUID userUuid;
	private int age;
	private Gender gender;
	private String nickname;
	private String token;
	private CompanyCategory companyCategory;
	private String profileImageUrl;


	public void setToken(String token) {
		this.token = token;
	}


	public void setCompanyCategory(CompanyCategory companyCategory) {
		this.companyCategory = companyCategory;
	}


	public void setAgeFromBirthdate(LocalDate birthdate) {
		this.age = LocalDate.now().getYear() - birthdate.getYear();
	}

}