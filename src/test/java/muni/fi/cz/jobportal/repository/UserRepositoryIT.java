package muni.fi.cz.jobportal.repository;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareUserEntity;
import static org.assertj.core.api.Assertions.assertThat;

import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("it")
class UserRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void findByEmailTest() {
    final var email = "email";
    userRepository.save(prepareUserEntity(email, JobPortalScope.COMPANY));

    assertThat(userRepository.findByEmail(email)).isPresent();
    assertThat(userRepository.findByEmail("random")).isEmpty();
  }

  @Test
  void existsByEmailTest() {
    final var email = "email";
    userRepository.save(prepareUserEntity(email, JobPortalScope.COMPANY));

    assertThat(userRepository.existsByEmail(email)).isTrue();
    assertThat(userRepository.existsByEmail("random")).isFalse();
  }

  @Test
  void isAdminTest() {
    final var admin = userRepository.save(prepareUserEntity("1", JobPortalScope.ADMIN)).getId();
    final var company = userRepository.save(prepareUserEntity("2", JobPortalScope.COMPANY)).getId();
    final var user = userRepository.save(prepareUserEntity("3", JobPortalScope.REGULAR_USER)).getId();

    assertThat(userRepository.isAdmin(admin)).isTrue();
    assertThat(userRepository.isAdmin(company)).isFalse();
    assertThat(userRepository.isAdmin(user)).isFalse();
  }

  @Test
  void isRegularUserTest() {
    final var admin = userRepository.save(prepareUserEntity("1", JobPortalScope.ADMIN)).getId();
    final var company = userRepository.save(prepareUserEntity("2", JobPortalScope.COMPANY)).getId();
    final var user = userRepository.save(prepareUserEntity("3", JobPortalScope.REGULAR_USER)).getId();

    assertThat(userRepository.isRegularUser(admin)).isFalse();
    assertThat(userRepository.isRegularUser(company)).isFalse();
    assertThat(userRepository.isRegularUser(user)).isTrue();
  }

  @Test
  void isCompanyTest() {
    final var admin = userRepository.save(prepareUserEntity("1", JobPortalScope.ADMIN)).getId();
    final var company = userRepository.save(prepareUserEntity("2", JobPortalScope.COMPANY)).getId();
    final var user = userRepository.save(prepareUserEntity("3", JobPortalScope.REGULAR_USER)).getId();

    assertThat(userRepository.isCompany(admin)).isFalse();
    assertThat(userRepository.isCompany(company)).isTrue();
    assertThat(userRepository.isCompany(user)).isFalse();
  }

}
