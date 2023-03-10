package muni.fi.cz.jobportal.service;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicantEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareApplicationEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCompanyEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.preparePositionEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareUserEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.annotation.IntegrationTest;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.event.handler.EventHandler;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.exception.IllegalStateChangeException;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
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
class ApplicationServiceIT extends AbstractIntegrationTest {

  @Autowired
  private ApplicationRepository applicationRepository;
  @Autowired
  private JobPositionRepository jobPositionRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ApplicationService applicationService;
  @MockBean
  private EventHandler eventHandler;
  @MockBean
  private AuthorityValidator authorityValidator;

  @Test
  void createTest() {
    final var request = new ApplicationCreateDto();
    request.setApplicant(userRepository.save(prepareApplicantEntity("email124")).getApplicant().getId());
    request.setJob(jobPositionRepository.save(
      preparePositionEntity(userRepository.save(prepareCompanyEntity("name", "company email")).getCompany(),
        PositionState.ACTIVE)).getId());
    applicationService.create(request);
    assertThat(applicationRepository.findAll()).hasSize(1).filteredOn(
      a -> a.getApplicant().getId().equals(request.getApplicant()) && a.getJobPosition().getId()
        .equals(request.getJob())).hasSize(1);
  }

  @Test
  void findOneTest() {
    final var randomUserCompany = userRepository.save(prepareCompanyEntity("random company", "random company email"));
    final var randomUserApplicant = userRepository.save(prepareApplicantEntity("random email124"));
    final var adminUser = userRepository.save(prepareUserEntity("admin", JobPortalScope.ADMIN));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email")).getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124")).getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)), ApplicationState.OPEN);
    applicationRepository.save(application);

    when(authorityValidator.getCurrentUser())
      .thenReturn(ownerCompany.getUser().getId())
      .thenReturn(ownerApplicant.getUser().getId())
      .thenReturn(randomUserCompany.getId())
      .thenReturn(randomUserApplicant.getId())
      .thenReturn(adminUser.getId());

    final var id = application.getId();
    final var randomId = UUID.randomUUID();
    // owner company
    assertEquals(application.getId(), assertDoesNotThrow(() -> applicationService.findOne(id)).getId());
    // owner applicant
    assertThrows(EntityNotFoundException.class, () -> applicationService.findOne(randomId));
    assertThat(applicationRepository.getOneByIdOrThrowNotFound(id).getState()).isEqualTo(ApplicationState.SEEN);
    // random company
    assertThrows(AccessDeniedException.class, () -> applicationService.findOne(id));
    // random applicant
    assertThrows(AccessDeniedException.class, () -> applicationService.findOne(id));
    // Admin
    assertEquals(application.getId(), assertDoesNotThrow(() -> applicationService.findOne(id)).getId());
  }

  @Test
  void findAllTest_bad() {
    final var pageable = Pageable.ofSize(10);
    final var paramsEmpty = ApplicationQueryParams.builder().build();
    final var randomUserCompany = userRepository.save(prepareCompanyEntity("random company", "random company email"));
    final var randomUserApplicant = userRepository.save(prepareApplicantEntity("random email124"));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email")).getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124")).getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)), ApplicationState.OPEN);
    final var paramsCompanyFail1 = ApplicationQueryParams.builder().applicant(ownerApplicant.getId()).build();
    final var paramsCompanyFail2 = ApplicationQueryParams.builder().company(ownerCompany.getId()).build();
    final var paramsCompanyFail3 = ApplicationQueryParams.builder().jobPosition(application.getJobPosition().getId())
      .build();
    final var paramsApplicantFail = ApplicationQueryParams.builder().company(ownerCompany.getId())
      .jobPosition(application.getJobPosition().getId()).build();
    applicationRepository.save(application);

    when(authorityValidator.getCurrentUser())
      .thenReturn(ownerApplicant.getUser().getId())
      .thenReturn(randomUserApplicant.getId())
      .thenReturn(randomUserCompany.getId());

    assertThrows(AccessDeniedException.class, () -> applicationService.findAll(pageable, paramsEmpty));
    assertThrows(AccessDeniedException.class, () -> applicationService.findAll(pageable, paramsApplicantFail));
    assertThrows(AccessDeniedException.class, () -> applicationService.findAll(pageable, paramsCompanyFail1));
    assertThrows(AccessDeniedException.class, () -> applicationService.findAll(pageable, paramsCompanyFail2));
    assertThrows(AccessDeniedException.class, () -> applicationService.findAll(pageable, paramsCompanyFail3));
  }

  @Test
  void findAllTest_happy() {
    final var pageable = Pageable.ofSize(10);
    final var paramsEmpty = ApplicationQueryParams.builder().build();
    final var adminUser = userRepository.save(prepareUserEntity("admin", JobPortalScope.ADMIN));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email")).getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124")).getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)), ApplicationState.OPEN);
    final var paramsCompanyHappy = ApplicationQueryParams.builder().applicant(null).company(ownerCompany.getId())
      .jobPosition(application.getJobPosition().getId()).build();
    final var paramsApplicantHappy = ApplicationQueryParams.builder().applicant(ownerApplicant.getId()).build();
    applicationRepository.saveAndFlush(application);

    when(authorityValidator.getCurrentUser())
      .thenReturn(adminUser.getId())
      .thenReturn(ownerCompany.getUser().getId())
      .thenReturn(ownerApplicant.getUser().getId());

    assertDoesNotThrow(() -> applicationService.findAll(pageable, paramsEmpty));
    assertDoesNotThrow(() -> applicationService.findAll(pageable, paramsCompanyHappy));
    assertDoesNotThrow(() -> applicationService.findAll(pageable, paramsApplicantHappy));
  }

  @Test
  void updateTest() {
    final var request = new ApplicationUpdateDto();
    request.setState(ApplicationState.OPEN);

    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email")).getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124")).getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)), ApplicationState.SEEN);

    when(authorityValidator.canManageApplication(any())).thenReturn(true);

    final var id = applicationRepository.saveAndFlush(application).getId();
    assertThrows(IllegalStateChangeException.class, () -> applicationService.update(id, request));
    request.setState(ApplicationState.SEEN);
    assertThrows(IllegalStateChangeException.class, () -> applicationService.update(id, request));
    request.setState(ApplicationState.CLOSED);
    assertThrows(IllegalStateChangeException.class, () -> applicationService.update(id, request));
    request.setState(ApplicationState.APPROVED);
    assertDoesNotThrow(() -> applicationService.update(id, request));
    verify(eventHandler).handleApplicationStateChange(any());
    request.setState(ApplicationState.DECLINED);
    assertDoesNotThrow(() -> applicationService.update(id, request));
    verify(eventHandler, times(2)).handleApplicationStateChange(any());
    assertDoesNotThrow(() -> applicationService.update(id, request));
    verify(eventHandler, times(2)).handleApplicationStateChange(any());
  }

  @Test
  void deleteTest() {
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email")).getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124")).getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)), ApplicationState.SEEN);
    final var id = applicationRepository.save(application).getId();

    when(authorityValidator.canDeleteApplication(any())).thenReturn(true);

    applicationService.delete(application.getId());
    assertThrows(EntityNotFoundException.class, () -> applicationService.delete(id));
    assertThat(applicationRepository.findAll()).isEmpty();
  }
}
