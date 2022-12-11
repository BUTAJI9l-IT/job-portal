package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

  @Query("SELECT CASE WHEN COUNT(t.token) > 0 THEN TRUE ELSE FALSE END FROM RefreshToken t WHERE t.token = :token")
  boolean exists(String token);

  @Modifying
  @Query("DELETE FROM RefreshToken t WHERE t.expires <= CURRENT_TIMESTAMP")
  void deleteExpired();
}
