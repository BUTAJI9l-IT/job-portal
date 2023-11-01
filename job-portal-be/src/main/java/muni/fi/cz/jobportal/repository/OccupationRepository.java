package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.Occupation;
import muni.fi.cz.jobportal.repository.search.OccupationSearchRepository;

import java.util.UUID;

public interface OccupationRepository extends OccupationSearchRepository, JobPortalRepository<Occupation, UUID> {

  @Override
  default Class<Occupation> getBaseClass() {
    return Occupation.class;
  }
}
