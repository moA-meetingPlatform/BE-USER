package com.moa.user.dto;


import com.moa.user.domain.OauthProviderType;
import lombok.Getter;


@Getter
public class OauthLoginDto {

	private OauthProviderType oauthProvider;
	private String oauthUserId;

}
