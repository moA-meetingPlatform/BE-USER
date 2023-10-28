package com.moa.user.vo.request;


import lombok.Getter;


@Getter
public class VerifyEmailRequest {

	private String email;
	private String code;

}
