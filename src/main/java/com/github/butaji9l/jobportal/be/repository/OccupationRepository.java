package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.Occupation;
import com.github.butaji9l.jobportal.be.repository.search.OccupationSearchRepository;
import java.util.UUID;

public interface OccupationRepository extends OccupationSearchRepository,
  JobPortalRepository<Occupation, UUID> {

  @Override
  default Class<Occupation> getBaseClass() {
    return Occupation.class;
  }
}
