package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.JobCategory;
import java.util.UUID;

public interface JobPositionCategoryRepository extends JobPortalRepository<JobCategory, UUID> {

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
