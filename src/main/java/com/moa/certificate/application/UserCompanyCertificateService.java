package com.moa.certificate.application;


import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import org.springframework.stereotype.Service;


@Service
public interface UserCompanyCertificateService {

	void createUserCompanyCertificate(CreateUserCompanyCertificateDto dto);

}
