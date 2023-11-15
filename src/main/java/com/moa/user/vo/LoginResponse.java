package com.moa.user.vo;


import com.moa.company.domain.CompanyCategory;
import com.moa.user.domain.Gender;
import lombok.Getter;

import java.util.UUID;


@Getter
public class LoginResponse {

	private UUID userUuid;
	private int age;
	private Gender gender;
	private String nickname;
	private String token;
	private CompanyCategory companyCategory;
	private String profileImageUrl;

}
