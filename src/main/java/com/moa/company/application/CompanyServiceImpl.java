package com.moa.company.application;


import com.moa.company.domain.Company;
import com.moa.company.domain.CompanyCategory;
import com.moa.company.infrastructure.CompanyRepository;
import com.moa.global.config.exception.CustomException;
import com.moa.global.config.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;


	/**
	 * 회사 id로 회사 카테고리 조회
	 *
	 * @param companyId
	 * @return companyCategory 회사 카테고리 ex. 1
	 */
	@Override
	public CompanyCategory getCompanyCategoryById(int companyId) {
		Company company = companyRepository.findById(companyId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESOURCE));
		return company.getCompanyCategory();
	}


	/**
	 * 회사 이메일 도메인으로 회사 id 조회
	 *
	 * @param companyEmailDomain 회사 이메일 도메인 ex. moa.com
	 * @return companyId
	 */
	@Override
	public Integer getCompanyIdByCompanyEmail(String companyEmailDomain) {
		Company company = companyRepository.findByCompanyEmail(companyEmailDomain).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESOURCE));
		return company.getId();
	}

}
