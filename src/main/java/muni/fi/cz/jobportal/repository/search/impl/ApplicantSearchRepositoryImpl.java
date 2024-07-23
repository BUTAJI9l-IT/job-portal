package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.repository.search.ApplicantSearchRepository;

public class ApplicantSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Applicant, ApplicantQueryParams> implements
  ApplicantSearchRepository {

  @Override
  public Class<Applicant> getBaseClass() {
    return Applicant.class;
  }
}
