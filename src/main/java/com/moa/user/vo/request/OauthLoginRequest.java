package com.moa.user.vo.request;


import com.moa.user.domain.OauthProviderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class OauthLoginRequest {

	@Schema(description = "로그인에 사용할 id(이메일형식)", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "KAKAO")
	private OauthProviderType oauthProvider;
	@Schema(description = "로그인에 사용할 id(서비스 제공자의 유저 고유 식별값)", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "example@moa.com")
	private String oauthUserId;

}
