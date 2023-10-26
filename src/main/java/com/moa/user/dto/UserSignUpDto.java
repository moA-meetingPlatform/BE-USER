package com.moa.user.dto;


import com.moa.user.domain.Gender;
import lombok.*;

import java.time.LocalDate;


@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {

	private String loginId;
	private String password;
	private String name;
	private LocalDate birthDate;
	private Gender gender;
	private String phoneNumber;
	private String nickname;
	private String introduction;

}
