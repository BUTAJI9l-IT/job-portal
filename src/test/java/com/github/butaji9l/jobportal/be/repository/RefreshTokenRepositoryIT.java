package com.github.butaji9l.jobportal.be.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.testutils.EntityUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.assertj.core.api.Assertions;
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
      EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user1", JobPortalScope.COMPANY)), valid),
      EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user2", JobPortalScope.COMPANY)), valid),
      EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user3", JobPortalScope.COMPANY)), valid),
      EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user4", JobPortalScope.COMPANY)), expired),
      EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user5", JobPortalScope.COMPANY)), expired)
    ));
    refreshTokenRepository.deleteExpired();
    Assertions.assertThat(refreshTokenRepository.findAll()).isNotEmpty().hasSize(3);
  }

  @Test
  void existsTest() {
    final var token = EntityUtils.prepareRefreshTokenEntity(userRepository.save(
        EntityUtils.prepareUserEntity("user1", JobPortalScope.COMPANY)),
      Instant.now());
    final var tokenId = "123";
    token.setToken(tokenId);
    refreshTokenRepository.save(token);
    assertThat(refreshTokenRepository.exists(tokenId)).isTrue();
    assertThat(refreshTokenRepository.exists("random")).isFalse();
  }

  @Test
  void deleteAllByUserIdTest() {
    final var user1 = userRepository.save(
      EntityUtils.prepareUserEntity("user1", JobPortalScope.COMPANY));
    final var user2 = userRepository.save(
      EntityUtils.prepareUserEntity("user2", JobPortalScope.COMPANY));
    refreshTokenRepository.saveAll(List.of(
      EntityUtils.prepareRefreshTokenEntity(user1, Instant.now()),
      EntityUtils.prepareRefreshTokenEntity(user2, Instant.now()),
      EntityUtils.prepareRefreshTokenEntity(user2, Instant.now()),
      EntityUtils.prepareRefreshTokenEntity(user2, Instant.now()),
      EntityUtils.prepareRefreshTokenEntity(user1, Instant.now())
    ));
    Assertions.assertThat(refreshTokenRepository.findAll()).hasSize(5);
    refreshTokenRepository.deleteAllByUserId(user1.getId());
    Assertions.assertThat(refreshTokenRepository.findAll()).hasSize(3);
    refreshTokenRepository.deleteAllByUserId(user2.getId());
    Assertions.assertThat(refreshTokenRepository.findAll()).isEmpty();
  }
}
