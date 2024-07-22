package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.JobCategory;

import java.util.UUID;

public interface JobPositionCategoryRepository extends JobPortalRepository<JobCategory, UUID> {

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
