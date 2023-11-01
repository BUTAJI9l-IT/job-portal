package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
import muni.fi.cz.jobportal.domain.JobPosition;
import muni.fi.cz.jobportal.repository.search.JobPositionSearchRepository;

public class JobPositionSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<JobPosition, JobPositionQueryParams> implements JobPositionSearchRepository {

  @Override
  public Class<JobPosition> getBaseClass() {
    return JobPosition.class;
  }
}
