package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.JobCategory;

public interface JobCategoryRepository extends AbstractJobPortalRepository<JobCategory, UUID> {

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
