package com.moa.company.infrastructure;


import com.moa.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByCompanyEmail(String companyEmail);

}
