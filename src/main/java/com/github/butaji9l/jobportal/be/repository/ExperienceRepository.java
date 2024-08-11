package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.Experience;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface ExperienceRepository extends JobPortalRepository<Experience, UUID> {

  @Query("SELECT e FROM Experience e WHERE e.company.id = :company")
  List<Experience> findExperiencesByCompany(UUID company);

  @Override
  default Class<Experience> getBaseClass() {
    return Experience.class;
  }
}
