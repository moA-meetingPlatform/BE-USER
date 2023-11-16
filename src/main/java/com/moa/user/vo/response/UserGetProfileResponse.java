package com.moa.user.vo.response;


import lombok.Getter;

import java.util.UUID;


@Getter
public class UserGetProfileResponse {

	private UUID userUuid;
	private String nickname;
	private String userIntroduce;
	private String profileImageUrl;

	private Integer reviewerCount;

	private Integer userMannersTemparature;

	private Integer userWarningScore;
	private boolean sameWithLoggedInUser;

}
