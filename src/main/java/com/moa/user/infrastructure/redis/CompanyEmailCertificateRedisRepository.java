package com.moa.user.infrastructure.redis;


import com.moa.user.domain.redis.CertificationCompanyEmail;
import org.springframework.data.repository.CrudRepository;


public interface CompanyEmailCertificateRedisRepository extends CrudRepository<CertificationCompanyEmail, String> {
}
