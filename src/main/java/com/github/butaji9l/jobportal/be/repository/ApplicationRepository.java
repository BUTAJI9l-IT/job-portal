package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.repository.search.ApplicationSearchRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRepository extends ApplicationSearchRepository,
  JobPortalRepository<Application, UUID> {

  @Query("""
    SELECT CASE
    WHEN COUNT(a) > 0
    THEN TRUE
    ELSE FALSE END
    FROM Application a WHERE a.applicant.id = :applicant AND a.jobPosition.id = :position AND a.state <> 'CLOSED'
    """)
  boolean existsActive(UUID applicant, UUID position);

  @Override
  default Class<Application> getBaseClass() {
    return Application.class;
  }
}
