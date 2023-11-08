package com.moa.user.vo.request;


import com.moa.user.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@ToString
public class UserSignUpRequest {

	@Schema(description = "로그인에 사용할 id(이메일형식)", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "example@moa.com")
	private String loginId;

	@Schema(description = "비밀번호", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
	private String password;

	@Schema(description = "이름", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "홍길동")
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "생년월일", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "2000-01-01")
	private LocalDate birthdate;

	@Schema(description = "성별", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "WOMAN")
	private Gender gender;

	@Schema(description = "전화번호", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "010-1234-5678")
	private String phoneNumber;

	@Schema(description = "닉네임", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED, example = "비누")
	private String nickname;

	@Schema(description = "자기소개", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "안녕하세요")
	private String introduction;

}
