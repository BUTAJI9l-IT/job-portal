package muni.fi.cz.jobportal.repository;

import java.util.Optional;
import java.util.UUID;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.repository.search.UserSearchRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends UserSearchRepository, JobPortalRepository<User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
  boolean existsByEmail(String email);
  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :id AND u.scope = 'ADMIN'")
  boolean isAdmin(UUID id);

  @Override
  default Class<User> getBaseClass() {
    return User.class;
  }
}
