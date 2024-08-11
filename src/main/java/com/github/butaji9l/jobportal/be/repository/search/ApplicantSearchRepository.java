package com.github.butaji9l.jobportal.be.repository.search;

import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import com.github.butaji9l.jobportal.be.domain.Applicant;

public interface ApplicantSearchRepository extends
  SearchRepository<Applicant, ApplicantQueryParams> {

}
