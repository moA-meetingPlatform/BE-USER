package com.moa.certificate.application;


import com.moa.certificate.domain.UserCompanyCertificate;
import com.moa.certificate.dto.CreateUserCompanyCertificateDto;
import com.moa.certificate.infrastructure.UserCompanyCertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserCompanyCertificateServiceImpl implements UserCompanyCertificateService {

	private final UserCompanyCertificateRepository userCompanyCertificateRepository;


	@Override
	public void createUserCompanyCertificate(CreateUserCompanyCertificateDto dto) {
		userCompanyCertificateRepository.save(new UserCompanyCertificate(dto.getUserId(), dto.getCompanyName(), dto.getCertificateImageUrl()));
	}

}
