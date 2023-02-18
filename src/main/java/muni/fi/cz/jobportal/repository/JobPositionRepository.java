package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.JobPosition;
import org.springframework.data.jpa.repository.Query;

public interface JobPositionRepository extends AbstractJobPortalRepository<JobPosition, UUID> {

  @Query(
    """
       SELECT CASE
       WHEN COUNT(jp) > 0 THEN TRUE
       ELSE FALSE END
       FROM JobPosition jp JOIN Application a ON a.jobPosition = jp.id
       WHERE a.applicant.user.id = :userId
      """
  )
  boolean userWithIdApplied(UUID userId);

  @Override
  default Class<JobPosition> getBaseClass() {
    return JobPosition.class;
  }
}
