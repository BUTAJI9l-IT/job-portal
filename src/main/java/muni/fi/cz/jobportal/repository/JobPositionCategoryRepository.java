package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.JobCategory;

public interface JobPositionCategoryRepository extends JobPortalRepository<JobCategory, UUID> {

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
