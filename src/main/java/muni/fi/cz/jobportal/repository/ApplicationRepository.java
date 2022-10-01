package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

}
