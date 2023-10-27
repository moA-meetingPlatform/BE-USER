package com.moa.user.domain.redis;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "phoneCertCode", timeToLive = 10)    // 10분 동안 유효한 인증번호
public class EmailVerifyCode {

	@Id
	private String email;
	private String code;

}