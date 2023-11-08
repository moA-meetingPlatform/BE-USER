package com.moa.user.dto;


import com.moa.user.domain.Gender;
import com.moa.user.vo.request.AgreeAdvertiseRequest;
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
	private LocalDate birthdate;
	private Gender gender;
	private String phoneNumber;
	private String nickname;
	private AgreeAdvertiseRequest agreeAdvertiseRequest;

}
