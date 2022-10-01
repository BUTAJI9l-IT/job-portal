package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

}
