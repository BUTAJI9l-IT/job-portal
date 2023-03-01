package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Experience;

public interface ExperienceRepository extends JobPortalRepository<Experience, UUID> {

  @Override
  default Class<Experience> getBaseClass() {
    return Experience.class;
  }
}
