package com.moa.user.vo.response;


import lombok.Getter;

import java.util.UUID;


@Getter
public class UserSearchInfoResponse {

	private UUID userUuid;
	private String nickname;
	private String profileImageUrl;

}
