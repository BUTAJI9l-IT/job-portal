package muni.fi.cz.jobportal.repository;

import java.util.Optional;
import java.util.UUID;
import muni.fi.cz.jobportal.domain.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends AbstractJobPortalRepository<User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
  boolean existsByEmail(String email);

  @Override
  default Class<User> getBaseClass() {
    return User.class;
  }
}
