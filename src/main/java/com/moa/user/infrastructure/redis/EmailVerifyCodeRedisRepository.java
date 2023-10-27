package com.moa.user.infrastructure.redis;


import com.moa.user.domain.redis.EmailVerifyCode;
import org.springframework.data.repository.CrudRepository;


public interface EmailVerifyCodeRedisRepository extends CrudRepository<EmailVerifyCode, String> {
}
