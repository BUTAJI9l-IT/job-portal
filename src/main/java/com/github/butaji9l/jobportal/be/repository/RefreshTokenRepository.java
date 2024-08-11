package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.RefreshToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JobPortalRepository<RefreshToken, String> {

  @Query("SELECT CASE WHEN COUNT(t.token) > 0 THEN TRUE ELSE FALSE END FROM RefreshToken t WHERE t.token = :token")
  boolean exists(String token);

  @Modifying
  @Query("DELETE FROM RefreshToken t WHERE t.expires <= CURRENT_TIMESTAMP")
  void deleteExpired();

  void deleteAllByUserId(UUID userId);

  @Override
  default Class<RefreshToken> getBaseClass() {
    return RefreshToken.class;
  }
}
