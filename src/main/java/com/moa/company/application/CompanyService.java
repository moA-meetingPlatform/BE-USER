package com.moa.company.application;


import com.moa.company.domain.CompanyCategory;
import com.moa.company.dto.CompanySimpleInfoDto;
import com.moa.global.config.exception.CustomException;


public interface CompanyService {

	/**
	 * 회사 id로 회사 카테고리 조회
	 *
	 * @param companyId
	 * @return
	 * @throws CustomException
	 */
	CompanyCategory getCompanyCategoryById(int companyId) throws CustomException;

	/**
	 * 회사 이메일 도메인으로 회사 id 조회
	 *
	 * @param emailDomain
	 * @return
	 * @throws CustomException
	 */
	Integer getCompanyIdByCompanyEmail(String emailDomain) throws CustomException;

	CompanySimpleInfoDto getCompanySimpleInfoById(int companyId) throws CustomException;

}
