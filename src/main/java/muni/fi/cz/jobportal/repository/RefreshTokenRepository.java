package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

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
