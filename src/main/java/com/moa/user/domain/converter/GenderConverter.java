package com.moa.user.domain.converter;


import com.moa.user.common.AbstractBaseEnumConverter;
import com.moa.user.domain.Gender;


public class GenderConverter extends AbstractBaseEnumConverter<Gender, Boolean, String> {

	public GenderConverter() {
		super(Gender.class);
	}

}
