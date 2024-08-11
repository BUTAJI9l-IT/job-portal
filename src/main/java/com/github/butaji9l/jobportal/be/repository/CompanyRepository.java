package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.repository.search.CompanySearchRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends CompanySearchRepository,
  JobPortalRepository<Company, UUID> {

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
