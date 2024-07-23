package com.github.butaji9l.jobportal.be.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.testutils.EntityUtils;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JobPositionRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  void userWithIdAppliedTest_happy() {
    final var userApplicant = userRepository.saveAndFlush(
      EntityUtils.prepareApplicantEntity("email1"));
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "email2"));
    final var jp = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(
      EntityUtils.prepareApplicationEntity(userApplicant.getApplicant(), jp,
        ApplicationState.OPEN));

    assertTrue(jobPositionRepository.userWithIdApplied(jp.getId(), userApplicant.getId()));
  }

  @Test
  void userWithIdAppliedTest_Bad() {
    final var userApplicant = userRepository.saveAndFlush(
      EntityUtils.prepareApplicantEntity("email1"));
    final var userApplicantZeroApplied = userRepository.saveAndFlush(
      EntityUtils.prepareApplicantEntity("email2"));
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(
      EntityUtils.prepareApplicationEntity(userApplicant.getApplicant(), jp,
        ApplicationState.CLOSED));

    assertFalse(jobPositionRepository.userWithIdApplied(jp.getId(), userApplicant.getId()));
    assertFalse(
      jobPositionRepository.userWithIdApplied(jp.getId(), userApplicantZeroApplied.getId()));
  }

  @Test
  void countAppliedTest() {
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    for (var status : ApplicationState.values()) {
      applicationRepository.save(EntityUtils.prepareApplicationEntity(
        userRepository.saveAndFlush(
          EntityUtils.prepareApplicantEntity("applicant" + status.toString())).getApplicant(), jp,
        status));
    }
    assertEquals(ApplicationState.values().length - 1,
      jobPositionRepository.countApplied(jp.getId()));
  }

  @Test
  void userWithIdLikedTest() {
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "emailCompany"));
    final var jp = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    for (int i = 0; i < 3; i++) {
      final var userApplicant = userRepository.saveAndFlush(
        EntityUtils.prepareApplicantEntity("email" + i));
      userApplicant.getApplicant().setSavedJobs(List.of(jp));
      userRepository.save(userApplicant);
      assertTrue(
        jobPositionRepository.applicantWithIdLiked(jp, userApplicant.getApplicant().getId()));
    }
    assertFalse(jobPositionRepository.applicantWithIdLiked(jp, UUID.randomUUID()));
  }
}
