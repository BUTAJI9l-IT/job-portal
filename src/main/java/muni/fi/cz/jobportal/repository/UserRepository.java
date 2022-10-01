package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
