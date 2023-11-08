package com.moa.user.dto;


import lombok.Getter;

import java.util.UUID;


@Getter
public class UserModifyDto {

	private UUID userUuid;
	private String nickname;
	private String introduce;
	private String profileImageUrl;

}
