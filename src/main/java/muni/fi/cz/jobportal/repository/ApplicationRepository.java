package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Application;
import muni.fi.cz.jobportal.repository.search.ApplicationSearchRepository;

public interface ApplicationRepository extends ApplicationSearchRepository, JobPortalRepository<Application, UUID> {

  @Override
  default Class<Application> getBaseClass() {
    return Application.class;
  }
}
