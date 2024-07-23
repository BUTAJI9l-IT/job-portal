package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.JobCategory;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface JobCategoryRepository extends JobPortalRepository<JobCategory, UUID> {

  @Query("SELECT o.category FROM Occupation o WHERE o.name = :occupation")
  Optional<JobCategory> findByOccupationName(String occupation);

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
