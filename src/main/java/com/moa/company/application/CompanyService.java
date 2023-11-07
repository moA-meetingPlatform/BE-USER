package com.moa.company.application;


import com.moa.global.config.exception.CustomException;


public interface CompanyService {

	Integer getCompanyIdByCompanyEmail(String emailDomain) throws CustomException;

}
