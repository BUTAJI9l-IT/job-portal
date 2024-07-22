package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.repository.search.ApplicantSearchRepository;

import java.util.UUID;

public interface ApplicantRepository extends ApplicantSearchRepository, JobPortalRepository<Applicant, UUID> {

  @Override
  default Class<Applicant> getBaseClass() {
    return Applicant.class;
  }
}
