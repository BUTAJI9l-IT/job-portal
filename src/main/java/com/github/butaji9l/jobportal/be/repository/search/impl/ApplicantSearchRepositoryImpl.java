package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.repository.search.ApplicantSearchRepository;

public class ApplicantSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<Applicant, ApplicantQueryParams> implements
  ApplicantSearchRepository {

  @Override
  public Class<Applicant> getBaseClass() {
    return Applicant.class;
  }
}
