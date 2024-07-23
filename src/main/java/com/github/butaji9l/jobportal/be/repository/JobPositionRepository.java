package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.repository.search.JobPositionSearchRepository;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface JobPositionRepository extends JobPositionSearchRepository,
  JobPortalRepository<JobPosition, UUID> {

  @Query(
    """
       SELECT CASE
       WHEN COUNT(jp) > 0 THEN TRUE
       ELSE FALSE END
       FROM JobPosition jp JOIN Application a ON a.jobPosition.id = jp.id
       WHERE a.applicant.user.id = :userId AND jp.id = :jobId AND a.state <> 'CLOSED'
      """
  )
  boolean userWithIdApplied(UUID jobId, UUID userId);

  @Query(
    """
       SELECT COUNT(jp)
       FROM JobPosition jp JOIN Application a ON a.jobPosition.id = jp.id
       WHERE jp.id = :jobId AND a.state <> 'CLOSED'
      """
  )
  int countApplied(UUID jobId);

  @Query(
    """
       SELECT CASE
       WHEN COUNT(a) > 0 THEN TRUE
       ELSE FALSE END
       FROM Applicant a WHERE a.id = :applicantId AND :jobPosition MEMBER OF a.savedJobs
      """
  )
  boolean applicantWithIdLiked(JobPosition jobPosition, UUID applicantId);

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
