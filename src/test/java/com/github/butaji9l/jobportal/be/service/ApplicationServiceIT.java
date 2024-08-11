package com.github.butaji9l.jobportal.be.service;

import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareApplicantEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareApplicationEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareCompanyEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.preparePositionEntity;
import static com.github.butaji9l.jobportal.be.testutils.EntityUtils.prepareUserEntity;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.butaji9l.jobportal.be.AbstractIntegrationTest;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.event.handler.EventHandler;
import com.github.butaji9l.jobportal.be.exception.EntityNotFoundException;
import com.github.butaji9l.jobportal.be.exception.IllegalStateChangeException;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;


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
    request.setApplicant(
      userRepository.save(prepareApplicantEntity("email124")).getApplicant().getId());
    request.setJob(jobPositionRepository.save(
      preparePositionEntity(
        userRepository.save(prepareCompanyEntity("name", "company email")).getCompany(),
        PositionState.ACTIVE)).getId());
    applicationService.create(request);
    Assertions.assertThat(applicationRepository.findAll()).hasSize(1).filteredOn(
      a -> a.getApplicant().getId().equals(request.getApplicant()) && a.getJobPosition().getId()
        .equals(request.getJob())).hasSize(1);
  }

  @Test
  void findOneTest() {
    final var randomUserCompany = userRepository.save(
      prepareCompanyEntity("random company", "random company email"));
    final var randomUserApplicant = userRepository.save(prepareApplicantEntity("random email124"));
    final var adminUser = userRepository.save(prepareUserEntity("admin", JobPortalScope.ADMIN));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email"))
      .getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124"))
      .getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)),
      ApplicationState.OPEN);
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
    assertEquals(application.getId(),
      assertDoesNotThrow(() -> applicationService.findOne(id)).getId());
    // owner applicant
    assertThrows(EntityNotFoundException.class, () -> applicationService.findOne(randomId));
    Assertions.assertThat(applicationRepository.getOneByIdOrThrowNotFound(id).getState())
      .isEqualTo(ApplicationState.SEEN);
    // random company
    assertThrows(AccessDeniedException.class, () -> applicationService.findOne(id));
    // random applicant
    assertThrows(AccessDeniedException.class, () -> applicationService.findOne(id));
    // Admin
    assertEquals(application.getId(),
      assertDoesNotThrow(() -> applicationService.findOne(id)).getId());
  }

  @Test
  void findAllTest_bad() {
    final var pageable = Pageable.ofSize(10);
    final var paramsEmpty = ApplicationQueryParams.builder().build();
    final var randomUserCompany = userRepository.save(
      prepareCompanyEntity("random company", "random company email"));
    final var randomUserApplicant = userRepository.save(prepareApplicantEntity("random email124"));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email"))
      .getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124"))
      .getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)),
      ApplicationState.OPEN);
    final var paramsCompanyFail1 = ApplicationQueryParams.builder()
      .applicant(ownerApplicant.getId()).build();
    final var paramsCompanyFail2 = ApplicationQueryParams.builder().company(ownerCompany.getId())
      .build();
    final var paramsCompanyFail3 = ApplicationQueryParams.builder()
      .jobPosition(application.getJobPosition().getId())
      .build();
    final var paramsApplicantFail = ApplicationQueryParams.builder().company(ownerCompany.getId())
      .jobPosition(application.getJobPosition().getId()).build();
    applicationRepository.save(application);

    when(authorityValidator.getCurrentUser())
      .thenReturn(ownerApplicant.getUser().getId())
      .thenReturn(randomUserApplicant.getId())
      .thenReturn(randomUserCompany.getId());

    assertThrows(AccessDeniedException.class,
      () -> applicationService.findAll(pageable, paramsEmpty));
    assertThrows(AccessDeniedException.class,
      () -> applicationService.findAll(pageable, paramsApplicantFail));
    assertThrows(AccessDeniedException.class,
      () -> applicationService.findAll(pageable, paramsCompanyFail1));
    assertThrows(AccessDeniedException.class,
      () -> applicationService.findAll(pageable, paramsCompanyFail2));
    assertThrows(AccessDeniedException.class,
      () -> applicationService.findAll(pageable, paramsCompanyFail3));
  }

  @Test
  void findAllTest_happy() {
    final var pageable = Pageable.ofSize(10);
    final var paramsEmpty = ApplicationQueryParams.builder().build();
    final var adminUser = userRepository.save(prepareUserEntity("admin", JobPortalScope.ADMIN));
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email"))
      .getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124"))
      .getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)),
      ApplicationState.OPEN);
    ownerCompany.setJobPositions(new ArrayList<>(List.of(application.getJobPosition())));
    userRepository.save(ownerCompany.getUser());
    final var paramsCompanyHappy = ApplicationQueryParams.builder().applicant(null)
      .company(ownerCompany.getId())
      .jobPosition(application.getJobPosition().getId()).build();
    final var paramsApplicantHappy = ApplicationQueryParams.builder()
      .applicant(ownerApplicant.getId()).build();
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

    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email"))
      .getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124"))
      .getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)),
      ApplicationState.SEEN);

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
    final var ownerCompany = userRepository.save(prepareCompanyEntity("name", "company email"))
      .getCompany();
    final var ownerApplicant = userRepository.save(prepareApplicantEntity("email124"))
      .getApplicant();
    final var application = prepareApplicationEntity(ownerApplicant,
      jobPositionRepository.save(preparePositionEntity(ownerCompany, PositionState.ACTIVE)),
      ApplicationState.SEEN);
    final var id = applicationRepository.save(application).getId();

    when(authorityValidator.canDeleteApplication(any())).thenReturn(true);

    applicationService.delete(application.getId());
    assertThrows(EntityNotFoundException.class, () -> applicationService.delete(id));
    Assertions.assertThat(applicationRepository.findAll()).isEmpty();
  }
}
