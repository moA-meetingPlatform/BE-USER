package com.moa.user.vo.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.UUID;


@Getter
public class SendEmailConfirmRequest {

	@Schema(description = "유저 UUID", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "123e4567-e89b-12d3-a456-426614174000")
	private UUID userUuid;

	@Schema(description = "인증코드를 받을 회사 이메일", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "moa.meetingservice@gmail.com")
	private String companyEmail;

	@Schema(description = "인증코드", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
	private String code;

}
