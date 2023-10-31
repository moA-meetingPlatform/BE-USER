package com.moa.user.domain.redis;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "emailVerifyCode", timeToLive = 600)    // 10분
public class EmailVerifyCode {

	@Id
	private String email;
	private String code;

}