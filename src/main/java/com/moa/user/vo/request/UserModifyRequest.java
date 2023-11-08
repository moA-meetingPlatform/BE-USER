package com.moa.user.vo.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;


@Getter
public class UserModifyRequest {

	@Schema(description = "유저 UUID", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "123e4567-e89b-12d3-a456-426614174000")
	private UUID userUuid;

	@Schema(description = "닉네임", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "모아아아")
	private String nickname;

	@Schema(description = "자기소개", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "안녕하세요")
	private String introduce;

	@Schema(description = "프로필 이미지 URL", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "profileImageUrl")
	private String profileImageUrl;

}
