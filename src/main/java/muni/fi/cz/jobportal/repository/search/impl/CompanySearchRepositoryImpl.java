package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.CompanyQueryParams;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.repository.search.CompanySearchRepository;

public class CompanySearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Company, CompanyQueryParams> implements CompanySearchRepository {

  @Override
  public Class<Company> getBaseClass() {
    return Company.class;
  }
}
