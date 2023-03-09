package muni.fi.cz.jobportal.repository;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicantEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicationEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCompanyEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.PositionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("it")
class ApplicationRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  void existsActiveQueryTest_happy() {
    final var userApplicant = userRepository.saveAndFlush(prepareApplicantEntity("email1"));
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "email2"));
    final var jp = jobPositionRepository.saveAndFlush(
        preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(prepareApplicationEntity(userApplicant.getApplicant(), jp, ApplicationState.OPEN));

    assertTrue(applicationRepository.existsActive(userApplicant.getApplicant().getId(), jp.getId()));
  }

  @Test
  void existsActiveQueryTest_bad() {
    final var userApplicant = userRepository.saveAndFlush(prepareApplicantEntity("email1"));
    final var userApplicantZeroApplied = userRepository.saveAndFlush(prepareApplicantEntity("email2"));
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
        preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(prepareApplicationEntity(userApplicant.getApplicant(), jp, ApplicationState.CLOSED));

    assertFalse(applicationRepository.existsActive(userApplicant.getApplicant().getId(), jp.getId()));
    assertFalse(applicationRepository.existsActive(userApplicantZeroApplied.getApplicant().getId(), jp.getId()));
  }
}
