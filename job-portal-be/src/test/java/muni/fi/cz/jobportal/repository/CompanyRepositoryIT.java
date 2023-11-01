package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.enums.PositionState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCompanyEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompanyRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private CompanyRepository companyRepository;

  @Test
  void userWithJobExistsTest() {
    final var userCompany = userRepository.saveAndFlush(prepareCompanyEntity("Name", "emailComp"));
    final var job1 = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.ACTIVE));
    final var job2 = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userCompany.getCompany(), PositionState.INACTIVE));
    final var job3 = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userRepository.saveAndFlush(prepareCompanyEntity("Another", "emailComp2")).getCompany(),
        PositionState.INACTIVE));

    assertTrue(companyRepository.userWithJobExists(userCompany.getId(), job1.getId()));
    assertTrue(companyRepository.userWithJobExists(userCompany.getId(), job2.getId()));
    assertFalse(companyRepository.userWithJobExists(userCompany.getId(), job3.getId()));
  }

}
