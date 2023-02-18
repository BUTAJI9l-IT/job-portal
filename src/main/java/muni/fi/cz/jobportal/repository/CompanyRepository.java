package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Company;

public interface CompanyRepository extends AbstractJobPortalRepository<Company, UUID> {

  @Override
  default Class<Company> getBaseClass() {
    return Company.class;
  }
}
