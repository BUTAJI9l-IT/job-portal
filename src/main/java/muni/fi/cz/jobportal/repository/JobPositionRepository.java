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
       WHERE a.applicant.user.id = :userId AND jp.id = :jobId
      """
  )
  boolean userWithIdApplied(UUID jobId, UUID userId);

  @Query(
    """
       SELECT CASE
       WHEN COUNT(a) > 0 THEN TRUE
       ELSE FALSE END
       FROM Applicant a WHERE a.user.id = :userId AND :jobPosition MEMBER OF a.savedJobs
      """
  )
  boolean userWithIdLiked(JobPosition jobPosition, UUID userId);

  @Override
  default Class<JobPosition> getBaseClass() {
    return JobPosition.class;
  }
}
