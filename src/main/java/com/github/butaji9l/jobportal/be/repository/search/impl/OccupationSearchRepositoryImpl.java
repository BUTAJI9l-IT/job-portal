package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.OccupationQueryParams;
import com.github.butaji9l.jobportal.be.domain.Occupation;
import com.github.butaji9l.jobportal.be.repository.search.OccupationSearchRepository;

public class OccupationSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Occupation, OccupationQueryParams> implements
  OccupationSearchRepository {

  @Override
  public Class<Occupation> getBaseClass() {
    return Occupation.class;
  }
}
