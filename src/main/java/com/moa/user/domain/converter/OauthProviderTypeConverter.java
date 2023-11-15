package com.moa.user.domain.converter;


import com.moa.user.domain.OauthProviderType;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Objects;


public class OauthProviderTypeConverter implements AttributeConverter<OauthProviderType, String> {

	@Override
	public String convertToDatabaseColumn(OauthProviderType attribute) {
		return attribute.getCode();
	}


	@Override
	public OauthProviderType convertToEntityAttribute(String dbData) {
		if (Objects.isNull(dbData)) {
			return null;
		}

		/*
			DB에서 읽어온 값이 BaseEnum의 code 값과 일치하는 Enum 클래스를 찾아서 반환한다.
			getEnumConstants()는 Enum 클래스의 모든 상수를 배열로 반환한다.
			일치하는 값이 없으면 IllegalArgumentException을 발생시킨다.
		 */
		return Arrays.stream(OauthProviderType.class.getEnumConstants())
			.filter(e -> e.getCode().equals(dbData))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
	}

}
