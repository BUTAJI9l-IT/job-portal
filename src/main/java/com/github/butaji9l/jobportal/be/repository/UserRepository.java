package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.repository.search.UserSearchRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends UserSearchRepository, JobPortalRepository<User, UUID> {

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :email")
  boolean existsByEmail(String email);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :id AND u.scope = 'ADMIN'")
  boolean isAdmin(UUID id);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :id AND u.scope = 'REGULAR_USER'")
  boolean isRegularUser(UUID id);

  @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.id = :id AND u.scope = 'COMPANY'")
  boolean isCompany(UUID id);

  @Override
  default Class<User> getBaseClass() {
    return User.class;
  }
}
