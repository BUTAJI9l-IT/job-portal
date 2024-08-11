package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.CompanyQueryParams;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.repository.search.CompanySearchRepository;

public class CompanySearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Company, CompanyQueryParams> implements
  CompanySearchRepository {

  @Override
  public Class<Company> getBaseClass() {
    return Company.class;
  }
}
