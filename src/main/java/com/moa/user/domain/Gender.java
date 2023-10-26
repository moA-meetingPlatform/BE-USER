package com.moa.user.domain;


import com.moa.user.common.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Gender implements BaseEnum<Boolean, String> {
	MEN(true, "남자"),
	WOMEN(false, "여자");
	private final Boolean code;
	private final String title;
}
