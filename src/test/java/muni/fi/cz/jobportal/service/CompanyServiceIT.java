package muni.fi.cz.jobportal.service;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCompanyEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareUserEntity;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.annotation.IntegrationTest;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.domain.Company;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
@WithMockUser
@ExtendWith(MockitoExtension.class)
class CompanyServiceIT extends AbstractIntegrationTest {

  @Autowired
  private CompanyRepository companyRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private CompanyService companyService;

  @Test
  void createTest() {
    final var request = loadResource("company_create_request.json", CompanyCreateDto.class);
    request.setUser(userRepository.save(prepareUserEntity("company", JobPortalScope.COMPANY)).getId());
    final var result = companyService.create(request);
    assertThat(userRepository.findAll()).hasSize(1);
    assertThat(companyRepository.findAll()).hasSize(1).map(Company::getId).containsExactly(result.getId());
  }

  @Test
  void deleteTest() {
    final var company = userRepository.save(prepareCompanyEntity("NAME", "email")).getCompany();
    final var jp = jobPositionRepository.saveAndFlush(preparePositionEntity(company, PositionState.ACTIVE));
    company.setJobPositions(new ArrayList<>(List.of(jp)));

    companyService.delete(companyRepository.saveAndFlush(company).getId());

    assertThat(jobPositionRepository.findAll()).isEmpty();
    assertThat(companyRepository.findAll()).isEmpty();
    assertThat(userRepository.findAll()).isEmpty();
  }
}
