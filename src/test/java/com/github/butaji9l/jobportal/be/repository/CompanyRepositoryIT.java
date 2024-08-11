package com.github.butaji9l.jobportal.be.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.testutils.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CompanyRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private CompanyRepository companyRepository;

  @Test
  void userWithJobExistsTest() {
    final var userCompany = userRepository.saveAndFlush(
      EntityUtils.prepareCompanyEntity("Name", "emailComp"));
    final var job1 = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    final var job2 = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userCompany.getCompany(), PositionState.INACTIVE));
    final var job3 = jobPositionRepository.saveAndFlush(
      EntityUtils.preparePositionEntity(userRepository.saveAndFlush(
          EntityUtils.prepareCompanyEntity("Another", "emailComp2")).getCompany(),
        PositionState.INACTIVE));

    assertTrue(companyRepository.userWithJobExists(userCompany.getId(), job1.getId()));
    assertTrue(companyRepository.userWithJobExists(userCompany.getId(), job2.getId()));
    assertFalse(companyRepository.userWithJobExists(userCompany.getId(), job3.getId()));
  }

}
