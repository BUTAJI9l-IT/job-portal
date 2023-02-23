package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Company;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends AbstractJobPortalRepository<Company, UUID> {

  @Query("""
    SELECT CASE
    WHEN COUNT (jp) > 0
    THEN TRUE ELSE FALSE END
    FROM JobPosition jp
    WHERE jp.id = :jobId AND jp.company.user = :userId
    """)
  boolean userWithJobExists(UUID userId, UUID jobId);

  @Override
  default Class<Company> getBaseClass() {
    return Company.class;
  }
}
