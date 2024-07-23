package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.OccupationQueryParams;
import muni.fi.cz.jobportal.domain.Occupation;
import muni.fi.cz.jobportal.repository.search.OccupationSearchRepository;

public class OccupationSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Occupation, OccupationQueryParams> implements
  OccupationSearchRepository {

  @Override
  public Class<Occupation> getBaseClass() {
    return Occupation.class;
  }
}
