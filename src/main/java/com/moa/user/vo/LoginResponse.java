package com.moa.user.vo;


import lombok.Getter;

import java.util.UUID;


@Getter
public class LoginResponse {

	private UUID userUuid;
	private String name;
	private String token;

}
