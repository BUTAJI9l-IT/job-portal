package com.github.butaji9l.jobportal.be.repository.search;

import com.github.butaji9l.jobportal.be.api.search.JobPositionQueryParams;
import com.github.butaji9l.jobportal.be.domain.JobPosition;

public interface JobPositionSearchRepository extends
  SearchRepository<JobPosition, JobPositionQueryParams> {

}
