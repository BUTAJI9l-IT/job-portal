package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Applicant;

public interface ApplicantRepository extends AbstractJobPortalRepository<Applicant, UUID> {

  @Override
  default Class<Applicant> getBaseClass() {
    return Applicant.class;
  }
}
