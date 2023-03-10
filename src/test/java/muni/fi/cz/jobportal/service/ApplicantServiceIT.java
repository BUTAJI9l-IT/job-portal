package muni.fi.cz.jobportal.service;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicantEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicationEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCompanyEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareExperienceEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.annotation.IntegrationTest;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import muni.fi.cz.jobportal.domain.Applicant;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
import muni.fi.cz.jobportal.repository.ExperienceRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.utils.AuthorityValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@IntegrationTest
@WithMockUser
@ExtendWith(MockitoExtension.class)
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
    request.setUser(userRepository.save(prepareUserEntity("email124", JobPortalScope.REGULAR_USER)).getId());

    final var result = applicantService.create(request);
    assertThat(userRepository.findAll()).hasSize(1);
    assertThat(applicantRepository.findAll()).hasSize(1).map(Applicant::getId).containsExactly(result.getId());
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

    when(authorityValidator.isAdmin()).thenReturn(true).thenReturn(false);
    when(authorityValidator.isCompany()).thenReturn(true).thenReturn(false);

    assertDoesNotThrow(() -> applicantService.findAll(pageable, params));
    assertDoesNotThrow(() -> applicantService.findAll(pageable, params));
    assertThrows(AccessDeniedException.class, () -> applicantService.findAll(pageable, params));
  }

  @Test
  void updateTest() {
    final var request = loadResource("applicant_update_request.json", ApplicantUpdateDto.class);
    final var id = userRepository.save(prepareApplicantEntity("email")).getApplicant().getId();

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.update(id, request);

    assertThat(applicantRepository.findById(id)).isPresent();
    final var applicant = applicantRepository.findById(id).get();
    assertEquals(request.getCity(), applicant.getCity());
    assertEquals(request.getName(), applicant.getUser().getName());
    assertEquals(request.getLastName(), applicant.getUser().getLastName());
    assertEquals(request.getCountry(), applicant.getCountry());
    assertEquals(request.getState(), applicant.getState());
    assertEquals(request.getPhone(), applicant.getPhone());
    assertEquals(request.getProfile(), applicant.getProfile());
  }

  @Test
  void deleteTest() {
    final var applicant = userRepository.save(prepareApplicantEntity("email")).getApplicant();
    final var jp = jobPositionRepository.saveAndFlush(
      preparePositionEntity(userRepository.saveAndFlush(prepareCompanyEntity("name", "company email")).getCompany(),
        PositionState.ACTIVE));
    applicant.setApplications(new ArrayList<>(
      List.of(applicationRepository.saveAndFlush(prepareApplicationEntity(applicant, jp, ApplicationState.OPEN)))));
    applicant.setExperiences(
      new ArrayList<>(List.of(experienceRepository.saveAndFlush(prepareExperienceEntity(applicant)))));

    applicantService.delete(applicantRepository.saveAndFlush(applicant).getId());

    assertThat(applicationRepository.findAll()).isEmpty();
    assertThat(experienceRepository.findAll()).isEmpty();
    assertThat(applicantRepository.findAll()).isEmpty();
    assertThat(userRepository.findAll()).hasSize(1);
  }

  @Test
  void addExperienceTest() {
    final var request = loadResource("experience_dto.json", ExperienceDto.class);
    request.setJobCategories(new ArrayList<>());
    request.getCompany().setId(null);
    final var id = userRepository.save(prepareApplicantEntity("email")).getApplicant().getId();

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.addExperience(id, request);

    assertThat(applicantRepository.findById(id)).isPresent();
    assertThat(experienceRepository.findAll()).hasSize(1).map(e -> e.getApplicant().getId()).containsExactly(id);
    final var applicant = applicantRepository.findById(id).get();
    assertThat(applicant.getExperiences()).hasSize(1).map(Experience::getOccupation)
      .containsExactly(request.getOccupation());
  }

  @Test
  void removeExperienceTest() {
    final var applicant = userRepository.save(prepareApplicantEntity("email")).getApplicant();
    final var experience = experienceRepository.save(prepareExperienceEntity(applicant));
    applicant.setExperiences(new ArrayList<>());
    applicant.getExperiences().add(experience);

    when(authorityValidator.isAdmin()).thenReturn(true);

    applicantService.removeExperience(applicantRepository.saveAndFlush(applicant).getId(), experience.getId());

    assertThat(applicantRepository.findById(applicant.getId())).isPresent();
    assertThat(experienceRepository.findAll()).isEmpty();
    final var applicantDb = applicantRepository.findById(applicant.getId()).get();
    assertThat(applicantDb.getExperiences()).isEmpty();
  }

  @Test
  void generateCVTest() {
    when(authorityValidator.isAdmin()).thenReturn(true);
    when(thymeleafService.generateCvPdf(any())).thenReturn(new ByteArrayInputStream(new byte[0]));
    applicantService.generateCV(UUID.randomUUID());
    verify(thymeleafService).generateCvPdf(any());
  }
}
