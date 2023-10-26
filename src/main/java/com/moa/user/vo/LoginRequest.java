package com.moa.user.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class LoginRequest {

	@Schema(description = "로그인에 사용할 id(이메일형식)", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "example@moa.com")
	private String loginId;
	@Schema(description = "비밀번호", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
	private String password;

}
