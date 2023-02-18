package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Application;

public interface ApplicationRepository extends AbstractJobPortalRepository<Application, UUID> {

  @Override
  default Class<Application> getBaseClass() {
    return Application.class;
  }
}
