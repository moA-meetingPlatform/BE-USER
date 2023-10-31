package com.moa.user.dto;


import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class VerifyEmailDto {

	private String email;
	private String code;

}
