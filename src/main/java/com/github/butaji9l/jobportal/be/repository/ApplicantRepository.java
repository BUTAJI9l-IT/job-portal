package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.repository.search.ApplicantSearchRepository;
import java.util.UUID;

public interface ApplicantRepository extends ApplicantSearchRepository,
  JobPortalRepository<Applicant, UUID> {

  @Override
  default Class<Applicant> getBaseClass() {
    return Applicant.class;
  }
}
