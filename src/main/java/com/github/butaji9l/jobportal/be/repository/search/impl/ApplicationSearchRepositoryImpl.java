package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;
import com.github.butaji9l.jobportal.be.domain.Application;
import com.github.butaji9l.jobportal.be.repository.search.ApplicationSearchRepository;

public class ApplicationSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Application, ApplicationQueryParams> implements
  ApplicationSearchRepository {

  @Override
  public Class<Application> getBaseClass() {
    return Application.class;
  }
}
