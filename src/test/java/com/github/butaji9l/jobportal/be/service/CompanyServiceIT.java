package com.github.butaji9l.jobportal.be.service;

import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareCompanyEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.preparePositionEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareUserEntity;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.domain.Company;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    request.setUser(
      userRepository.save(prepareUserEntity("company", JobPortalScope.COMPANY)).getId());
    final var result = companyService.create(request);
    Assertions.assertThat(userRepository.findAll()).hasSize(1);
    Assertions.assertThat(companyRepository.findAll()).hasSize(1).map(Company::getId)
      .containsExactly(result.getId());
  }

  @Test
  void deleteTest() {
    final var company = userRepository.save(prepareCompanyEntity("NAME", "email")).getCompany();
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(company, PositionState.ACTIVE));
    company.setJobPositions(new ArrayList<>(List.of(jp)));

    companyService.delete(companyRepository.saveAndFlush(company).getId());

    Assertions.assertThat(jobPositionRepository.findAll()).isEmpty();
    Assertions.assertThat(companyRepository.findAll()).isEmpty();
    Assertions.assertThat(userRepository.findAll()).isEmpty();
  }
}
