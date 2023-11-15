package com.moa.user.domain;


import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum OauthProviderType {
	NAVER("NA"), KAKAO("KA"), APPLE("AP");

	private final String code;


	public static OauthProviderType find(String value) {
		try {
			return valueOf(value);
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}
	}
}