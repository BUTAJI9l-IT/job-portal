package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.repository.search.CompanySearchRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CompanyRepository extends CompanySearchRepository, JobPortalRepository<Company, UUID> {

  @Query("""
    SELECT CASE
    WHEN COUNT (jp) > 0
    THEN TRUE ELSE FALSE END
    FROM JobPosition jp
    WHERE jp.id = :jobId AND jp.company.user.id = :userId
    """)
  boolean userWithJobExists(UUID userId, UUID jobId);

  @Override
  default Class<Company> getBaseClass() {
    return Company.class;
  }
}
