package muni.fi.cz.jobportal.repository;

import java.util.Optional;
import java.util.UUID;
import muni.fi.cz.jobportal.domain.JobCategory;
import org.springframework.data.jpa.repository.Query;

public interface JobCategoryRepository extends JobPortalRepository<JobCategory, UUID> {

  @Query("SELECT o.category FROM Occupation o WHERE o.name = :occupation")
  Optional<JobCategory> findByOccupationName(String occupation);

  @Override
  default Class<JobCategory> getBaseClass() {
    return JobCategory.class;
  }
}
