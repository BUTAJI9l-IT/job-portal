package muni.fi.cz.jobportal.repository;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareRefreshTokenEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareUserEntity;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RefreshTokenRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  void deleteExpiredTest() {
    final var expired = Instant.now().minus(1L, ChronoUnit.DAYS);
    final var valid = Instant.now().plus(1L, ChronoUnit.DAYS);
    refreshTokenRepository.saveAll(List.of(
      prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user1", JobPortalScope.COMPANY)), valid),
      prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user2", JobPortalScope.COMPANY)), valid),
      prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user3", JobPortalScope.COMPANY)), valid),
      prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user4", JobPortalScope.COMPANY)), expired),
      prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user5", JobPortalScope.COMPANY)), expired)
    ));
    refreshTokenRepository.deleteExpired();
    assertThat(refreshTokenRepository.findAll()).isNotEmpty().hasSize(3);
  }

  @Test
  void existsTest() {
    final var token = prepareRefreshTokenEntity(userRepository.save(prepareUserEntity("user1", JobPortalScope.COMPANY)),
      Instant.now());
    final var tokenId = "123";
    token.setToken(tokenId);
    refreshTokenRepository.save(token);
    assertThat(refreshTokenRepository.exists(tokenId)).isTrue();
    assertThat(refreshTokenRepository.exists("random")).isFalse();
  }

  @Test
  void deleteAllByUserIdTest() {
    final var user1 = userRepository.save(prepareUserEntity("user1", JobPortalScope.COMPANY));
    final var user2 = userRepository.save(prepareUserEntity("user2", JobPortalScope.COMPANY));
    refreshTokenRepository.saveAll(List.of(
      prepareRefreshTokenEntity(user1, Instant.now()),
      prepareRefreshTokenEntity(user2, Instant.now()),
      prepareRefreshTokenEntity(user2, Instant.now()),
      prepareRefreshTokenEntity(user2, Instant.now()),
      prepareRefreshTokenEntity(user1, Instant.now())
    ));
    assertThat(refreshTokenRepository.findAll()).hasSize(5);
    refreshTokenRepository.deleteAllByUserId(user1.getId());
    assertThat(refreshTokenRepository.findAll()).hasSize(3);
    refreshTokenRepository.deleteAllByUserId(user2.getId());
    assertThat(refreshTokenRepository.findAll()).isEmpty();
  }
}
