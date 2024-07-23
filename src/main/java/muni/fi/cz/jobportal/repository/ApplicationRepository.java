package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.repository.search.ApplicationSearchRepository;
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
