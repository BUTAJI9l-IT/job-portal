package com.github.butaji9l.jobportal.be.repository.search;

import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;
import com.github.butaji9l.jobportal.be.domain.Application;

public interface ApplicationSearchRepository extends
  SearchRepository<Application, ApplicationQueryParams> {

}
