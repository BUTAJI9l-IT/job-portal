package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.PositionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.testutils.EntityUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class JobPositionRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  void userWithIdAppliedTest_happy() {
    final var userApplicant = userRepository.saveAndFlush(prepareApplicantEntity("email1"));
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "email2"));
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(prepareApplicationEntity(userApplicant.getApplicant(), jp, ApplicationState.OPEN));

    assertTrue(jobPositionRepository.userWithIdApplied(jp.getId(), userApplicant.getId()));
  }

  @Test
  void userWithIdAppliedTest_Bad() {
    final var userApplicant = userRepository.saveAndFlush(prepareApplicantEntity("email1"));
    final var userApplicantZeroApplied = userRepository.saveAndFlush(prepareApplicantEntity("email2"));
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(prepareApplicationEntity(userApplicant.getApplicant(), jp, ApplicationState.CLOSED));

    assertFalse(jobPositionRepository.userWithIdApplied(jp.getId(), userApplicant.getId()));
    assertFalse(jobPositionRepository.userWithIdApplied(jp.getId(), userApplicantZeroApplied.getId()));
  }

  @Test
  void countAppliedTest() {
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    for (var status : ApplicationState.values()) {
      applicationRepository.save(prepareApplicationEntity(
        userRepository.saveAndFlush(prepareApplicantEntity("applicant" + status.toString())).getApplicant(), jp,
        status));
    }
    assertEquals(ApplicationState.values().length - 1, jobPositionRepository.countApplied(jp.getId()));
  }

  @Test
  void userWithIdLikedTest() {
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    for (int i = 0; i < 3; i++) {
      final var userApplicant = userRepository.saveAndFlush(prepareApplicantEntity("email" + i));
      userApplicant.getApplicant().setSavedJobs(List.of(jp));
      userRepository.save(userApplicant);
      assertTrue(jobPositionRepository.applicantWithIdLiked(jp, userApplicant.getApplicant().getId()));
    }
    assertFalse(jobPositionRepository.applicantWithIdLiked(jp, UUID.randomUUID()));
  }
}
