package com.moa.user.dto;


import lombok.*;

import java.util.UUID;


@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDto {

	private Long id;
	private String loginId;
	private String name;
	private String email;
	private String phone;
	private UUID userUuid;
	private String token;

}