package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.JobPositionQueryParams;
import com.github.butaji9l.jobportal.be.domain.JobPosition;
import com.github.butaji9l.jobportal.be.repository.search.JobPositionSearchRepository;

public class JobPositionSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<JobPosition, JobPositionQueryParams> implements
  JobPositionSearchRepository {

  @Override
  public Class<JobPosition> getBaseClass() {
    return JobPosition.class;
  }
}
