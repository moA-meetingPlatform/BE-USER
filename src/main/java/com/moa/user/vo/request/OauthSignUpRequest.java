package com.moa.user.vo.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.moa.user.domain.Gender;
import com.moa.user.domain.OauthProviderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
public class OauthSignUpRequest {

	@Schema(description = "OAuth 제공자", example = "KAKAO")
	private OauthProviderType oauthProvider;

	@Schema(description = "OAuth 사용자 ID", example = "1234567890")
	private String oauthUserId;

	@Schema(description = "이름", example = "김모모")
	private String name;

	@Schema(description = "생년월일", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "20000101", pattern = "yyyyMMdd", type = "string")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
	@DateTimeFormat(pattern = "yyyyMMdd")
	private LocalDate birthdate;

	@Schema(description = "성별", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "WOMAN")
	private Gender gender;

	@Schema(description = "전화번호", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "01012345678")
	private String phoneNumber;

	@Schema(description = "닉네임", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "비누")
	private String nickname;

	@Schema(description = "이메일 수신 동의 여부", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
	private AgreeAdvertiseRequest agreeAdvertiseRequest;

	@Schema(description = "회사 이메일 인증 정보", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private SignUpVerifyCompanyEmailRequest verifyCompanyEmailRequest;

	@Schema(description = "회사 증명서(재직증명서) 인증 정보", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	private SignUpVerifyCompanyCertificateRequest verifyCompanyCertificateRequest;

}
