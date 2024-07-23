package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.ApplicationDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicationDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.event.ApplicationStateChangedEvent;
import com.github.butaji9l.jobportal.be.exception.IllegalStateChangeException;
import com.github.butaji9l.jobportal.be.mapper.ApplicationMapper;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.ApplicationService;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link ApplicationService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final UserRepository userRepository;
  private final ApplicationMapper applicationMapper;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final AuthorityValidator authorityValidator;

  @NonNull
  @Override
  public ApplicationDetailDto create(@NonNull ApplicationCreateDto payload) {
    return applicationMapper.map(
      applicationRepository.saveAndFlush(applicationMapper.map(payload)));
  }

  @NonNull
  @Override
  public ApplicationDetailDto findOne(@NonNull UUID id) {
    final var currentUser = userRepository.getOneByIdOrThrowNotFound(
      authorityValidator.getCurrentUser());
    final var application = applicationRepository.getOneByIdOrThrowNotFound(id);
    if (currentUser.getScope().equals(JobPortalScope.COMPANY)) {
      if (!application.getJobPosition().getCompany().getUser().getId()
        .equals(currentUser.getId())) {
        throw new AccessDeniedException(
          "Access denied: Cannot see an application for another company's job position");
      }
      if (application.getState().equals(ApplicationState.OPEN)) {
        application.setState(ApplicationState.SEEN);
        applicationRepository.saveAndFlush(application);
      }
    } else if (currentUser.getScope().equals(JobPortalScope.REGULAR_USER)
      && (!application.getApplicant().getUser()
      .getId().equals(currentUser.getId()))) {
      throw new AccessDeniedException("Access denied: Cannot see another applicant's application");
    }
    return applicationMapper.map(application);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<ApplicationDto> findAll(Pageable pageable, ApplicationQueryParams params) {
    checkParamPermissions(params,
      userRepository.getOneByIdOrThrowNotFound(authorityValidator.getCurrentUser()));
    return applicationRepository.search(pageable, params).map(applicationMapper::mapDto);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.canManageApplication(#id)")
  public ApplicationDetailDto update(@NonNull UUID id, @NonNull ApplicationUpdateDto payload) {
    final var application = applicationRepository.getOneByIdOrThrowNotFound(id);
    if (application.getState().getState() > payload.getState().getState() || payload.getState()
      .equals(ApplicationState.SEEN) || payload.getState().equals(ApplicationState.CLOSED)) {
      throw new IllegalStateChangeException();
    }
    final var isChanged = !application.getState().equals(payload.getState());
    final var updated = applicationMapper.map(
      applicationRepository.saveAndFlush(applicationMapper.update(application, payload)));
    if (isChanged) {
      if (updated.getState().equals(ApplicationState.APPROVED)) {
        applicationEventPublisher.publishEvent(ApplicationStateChangedEvent.toApproved(this, id));
      } else if (updated.getState().equals(ApplicationState.DECLINED)) {
        applicationEventPublisher.publishEvent(ApplicationStateChangedEvent.toDeclined(this, id));
      }
    }
    return updated;
  }

  @Override
  @PreAuthorize("@authorityValidator.canDeleteApplication(#id)")
  public void delete(@NonNull UUID id) {
    applicationRepository.delete(applicationRepository.getOneByIdOrThrowNotFound(id));
  }

  private static void checkParamPermissions(ApplicationQueryParams params, User user) {
    if (user.getScope().equals(JobPortalScope.ADMIN)) {
      return;
    }
    if (params.getJobPosition() == null && params.getApplicant() == null
      && params.getCompany() == null) {
      throw new AccessDeniedException(
        "Access denied: not allowed to browse through all applications");
    }
    if (user.getScope().equals(JobPortalScope.COMPANY) &&
      (params.getApplicant() != null ||
        (params.getCompany() != null && !params.getCompany().equals(user.getCompany().getId())) ||
        (params.getJobPosition() != null &&
          (user.getCompany().getJobPositions() == null || user.getCompany().getJobPositions()
            .stream()
            .noneMatch(jp -> jp.getId().equals(params.getJobPosition())))))) {
      throw new AccessDeniedException(
        "Access denied: Company is not allowed to filter by another company, another companies' jobs, or see all applications of users");
    }
    if (user.getScope().equals(JobPortalScope.REGULAR_USER) && !user.getApplicant().getId()
      .equals(params.getApplicant())) {
      throw new AccessDeniedException(
        "Access denied: Applicant is not allowed to see another applicants' applications");
    }
  }
}
