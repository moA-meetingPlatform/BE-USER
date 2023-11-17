package com.moa.certificate.infrastructure;


import com.moa.certificate.domain.UserCompanyCertificate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCompanyCertificateRepository extends JpaRepository<UserCompanyCertificate, Long> {
}
