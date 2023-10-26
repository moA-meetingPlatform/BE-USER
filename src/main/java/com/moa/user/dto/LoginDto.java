package com.moa.user.dto;


import lombok.*;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	private String loginId;     // email 형식의 loginId
	private String password;

}
