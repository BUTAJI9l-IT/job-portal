package muni.fi.cz.jobportal.repository;

import java.util.List;
import java.util.UUID;
import muni.fi.cz.jobportal.domain.Experience;
import org.springframework.data.jpa.repository.Query;

public interface ExperienceRepository extends JobPortalRepository<Experience, UUID> {

  @Query("SELECT e FROM Experience e WHERE e.company.id = :company")
  List<Experience> findExperiencesByCompany(UUID company);

  @Override
  default Class<Experience> getBaseClass() {
    return Experience.class;
  }
}
