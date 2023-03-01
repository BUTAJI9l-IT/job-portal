package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.repository.search.ApplicationSearchRepository;

public class ApplicationSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Application, ApplicationQueryParams> implements ApplicationSearchRepository {

  @Override
  public Class<Application> getBaseClass() {
    return Application.class;
  }
}
