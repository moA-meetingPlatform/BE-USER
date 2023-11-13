package com.moa.user.dto;


import com.moa.company.domain.CompanyCategory;
import com.moa.user.domain.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultInfoDto {

	private UUID userUuid;
	private int birthYear;
	private Gender gender;
	private String nickname;
	private String token;
	private CompanyCategory companyCategory;


	public void setBirthYearFromBirthdate(LocalDate birthdate) {
		this.birthYear = birthdate.getYear();
	}

}