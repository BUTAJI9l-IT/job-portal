package com.github.butaji9l.jobportal.be.service;

import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareApplicantEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareApplicationEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareCompanyEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareExperienceEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.preparePositionEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareUserEntity;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import com.github.butaji9l.jobportal.be.domain.Applicant;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.exception.EntityNotFoundException;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.repository.ExperienceRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

class ApplicantServiceIT extends AbstractIntegrationTest {

  @MockBean
  private ThymeleafService thymeleafService;
  @MockBean
  private AuthorityValidator authorityValidator;
  @Autowired
  private ApplicantRepository applicantRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ExperienceRepository experienceRepository;
  @Autowired
  private ApplicantService applicantService;
  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;

  @Test
  void createTest() {
    final var request = new ApplicantCreateDto();
    request.setUser(
      userRepository.save(prepareUserEntity("email124", JobPortalScope.REGULAR_USER)).getId());

    final var result = applicantService.create(request);
    Assertions.assertThat(userRepository.findAll()).hasSize(1);
    Assertions.assertThat(applicantRepository.findAll()).hasSize(1).map(Applicant::getId)
      .containsExactly(result.getId());
  }

  @Test
  void findOneTest() {
    final var randomUuid = UUID.randomUUID();
    final var exists = userRepository.save(prepareApplicantEntity("email")).getApplicant().getId();
    when(authorityValidator.isRegularUser()).thenReturn(false).thenReturn(true);
    when(authorityValidator.isCurrentApplicant(exists)).thenReturn(true).thenReturn(false);

    assertThrows(EntityNotFoundException.class, () -> applicantService.findOne(randomUuid));
    assertDoesNotThrow(() -> applicantService.findOne(exists));
    assertThrows(AccessDeniedException.class, () -> applicantService.findOne(exists));
  }

  @Test
  void findAllTest_empty() {
    final var pageable = Pageable.ofSize(10);
    final var params = ApplicantQueryParams.builder().build();

    final var admin = userRepository.save(prepareUserEntity("adminemail", JobPortalScope.ADMIN));
    final var company = userRepository.save(prepareCompanyEntity("company", "email@comp.com"));

    when(authorityValidator.isAdmin()).thenReturn(true).thenReturn(false);
    when(authorityValidator.isCompany()).thenReturn(true).thenReturn(false);
    when(authorityValidator.getCurrentUser()).thenReturn(admin.getId()).thenReturn(company.getId());

    assertDoesNotThrow(() -> applicantService.findAll(pageable, params));
    assertThrows(AccessDeniedException.class, () -> applicantService.findAll(pageable, params));
    assertThrows(AccessDeniedException.class, () -> applicantService.findAll(pageable, params));
  }

  @Test
  void updateTest() {
    final var request = loadResource("applicant_update_request.json", ApplicantUpdateDto.class);
    final var id = userRepository.save(prepareApplicantEntity("email")).getApplicant().getId();

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.update(id, request);

    Assertions.assertThat(applicantRepository.findById(id)).isPresent();
    final var applicant = applicantRepository.findById(id).get();
    org.junit.jupiter.api.Assertions.assertEquals(request.getCity(), applicant.getCity());
    org.junit.jupiter.api.Assertions.assertEquals(request.getName(), applicant.getUser().getName());
    org.junit.jupiter.api.Assertions.assertEquals(request.getLastName(),
      applicant.getUser().getLastName());
    org.junit.jupiter.api.Assertions.assertEquals(request.getCountry(), applicant.getCountry());
    org.junit.jupiter.api.Assertions.assertEquals(request.getState(), applicant.getState());
    org.junit.jupiter.api.Assertions.assertEquals(request.getPhone(), applicant.getPhone());
    org.junit.jupiter.api.Assertions.assertEquals(request.getProfile(), applicant.getProfile());
  }

  @Test
  void deleteTest() {
    final var applicant = userRepository.save(prepareApplicantEntity("email")).getApplicant();
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(
        userRepository.saveAndFlush(prepareCompanyEntity("name", "company email")).getCompany(),
        PositionState.ACTIVE));
    applicant.setApplications(new ArrayList<>(
      List.of(applicationRepository.saveAndFlush(
        prepareApplicationEntity(applicant, jp, ApplicationState.OPEN)))));
    applicant.setExperiences(
      new ArrayList<>(
        List.of(experienceRepository.saveAndFlush(prepareExperienceEntity(applicant)))));

    applicantService.delete(applicantRepository.saveAndFlush(applicant).getId());

    Assertions.assertThat(applicationRepository.findAll()).isEmpty();
    Assertions.assertThat(experienceRepository.findAll()).isEmpty();
    Assertions.assertThat(applicantRepository.findAll()).isEmpty();
    Assertions.assertThat(userRepository.findAll()).hasSize(1);
  }

  @Test
  void addExperienceTest() {
    final var request = loadResource("experience_dto.json", ExperienceDto.class);
    request.setJobCategories(new ArrayList<>());
    request.getCompany().setId(null);
    final var id = userRepository.save(prepareApplicantEntity("email")).getApplicant().getId();

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.addExperience(id, request);

    Assertions.assertThat(applicantRepository.findById(id)).isPresent();
    Assertions.assertThat(experienceRepository.findAll()).hasSize(1)
      .map(e -> e.getApplicant().getId()).containsExactly(id);
    final var applicant = applicantRepository.findById(id).get();
    Assertions.assertThat(applicant.getExperiences()).hasSize(1).map(Experience::getOccupation)
      .containsExactly(request.getOccupation());
  }

  @Test
  void removeExperienceTest() {
    final var applicant = userRepository.save(prepareApplicantEntity("email")).getApplicant();
    final var experience = experienceRepository.save(prepareExperienceEntity(applicant));
    applicant.setExperiences(new ArrayList<>());
    applicant.getExperiences().add(experience);

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.removeExperience(applicantRepository.saveAndFlush(applicant).getId(),
      experience.getId());

    Assertions.assertThat(applicantRepository.findById(applicant.getId())).isPresent();
    Assertions.assertThat(experienceRepository.findAll()).isEmpty();
    final var applicantDb = applicantRepository.findById(applicant.getId()).get();
    Assertions.assertThat(applicantDb.getExperiences()).isEmpty();
  }

  @Test
  void generateCVTest() {
    when(authorityValidator.isAdmin()).thenReturn(true);
    when(thymeleafService.generateCvPdf(any())).thenReturn(new ByteArrayInputStream(new byte[0]));
    applicantService.generateCV(UUID.randomUUID());
    verify(thymeleafService).generateCvPdf(any());
  }
}
