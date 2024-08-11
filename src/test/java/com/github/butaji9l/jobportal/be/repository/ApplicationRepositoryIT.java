package com.github.butaji9l.jobportal.be.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.testutils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ApplicationRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private UserRepository userRepository;

  @Test
  void existsActiveQueryTest_happy() {
    final var userApplicant = userRepository.saveAndFlush(
      EntityUtils.prepareApplicantEntity("email1"));
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "email2"));
    final var jp = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    applicationRepository.save(
      EntityUtils.prepareApplicationEntity(userApplicant.getApplicant(), jp,
        ApplicationState.OPEN));

    assertTrue(
      applicationRepository.existsActive(userApplicant.getApplicant().getId(), jp.getId()));
  }

  @Test
  void existsActiveQueryTest_bad() {
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

    assertFalse(
      applicationRepository.existsActive(userApplicant.getApplicant().getId(), jp.getId()));
    assertFalse(applicationRepository.existsActive(userApplicantZeroApplied.getApplicant().getId(),
      jp.getId()));
  }
}
