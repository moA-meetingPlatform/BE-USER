package com.moa.user.domain.redis;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@RedisHash(value = "CertificationCompanyEmail", timeToLive = 600)    // 단위: second, 60*10 => 10분
public class CertificationCompanyEmail {

	@Id
	private String companyEmail;
	private String code;
	private Integer companyId;

}