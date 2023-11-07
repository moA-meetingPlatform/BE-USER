package com.moa.user.vo.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


@Getter
public class SendEmailRequest {

	@Schema(description = "인증코드를 받을 이메일", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "moa.meetingservice@gmail.com")
	private String companyEmail;

}
