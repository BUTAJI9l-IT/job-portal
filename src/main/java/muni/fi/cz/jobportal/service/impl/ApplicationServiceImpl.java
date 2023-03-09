package muni.fi.cz.jobportal.service.impl;

import static muni.fi.cz.jobportal.utils.AuthenticationUtils.getCurrentUser;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.api.detail.ApplicationDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.event.ApplicationStateChangedEvent;
import muni.fi.cz.jobportal.exception.IllegalStateChangeException;
import muni.fi.cz.jobportal.mapper.ApplicationMapper;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.ApplicationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final UserRepository userRepository;
  private final ApplicationMapper applicationMapper;
  private final ApplicationEventPublisher applicationEventPublisher;

  @NonNull
  @Override
  public ApplicationDetailDto create(@NonNull ApplicationCreateDto payload) {
    return applicationMapper.map(applicationRepository.saveAndFlush(applicationMapper.map(payload)));
  }

  @NonNull
  @Override
  public ApplicationDetailDto findOne(@NonNull UUID id) {
    final var currentUser = userRepository.getOneByIdOrThrowNotFound(getCurrentUser());
    final var application = applicationRepository.getOneByIdOrThrowNotFound(id);
    if (application.getState().equals(ApplicationState.OPEN) && currentUser.getCompany() != null
      && currentUser.getCompany().getId().equals(application.getJobPosition().getCompany().getId())) {
      application.setState(ApplicationState.SEEN);
      applicationRepository.saveAndFlush(application);
    }
    return applicationMapper.map(application);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<ApplicationDto> findAll(Pageable pageable, ApplicationQueryParams params) {
    checkParamPermissions(params, userRepository.getOneByIdOrThrowNotFound(getCurrentUser()));
    return applicationRepository.search(pageable, params).map(applicationMapper::mapDto);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.canManageApplication(#id)")
  public ApplicationDetailDto update(@NonNull UUID id, @NonNull ApplicationUpdateDto payload) {
    final var application = applicationRepository.getOneByIdOrThrowNotFound(id);
    if (application.getState().getState() > payload.getState().getState()) {
      throw new IllegalStateChangeException();
    }
    final var isChanged = application.getState().equals(payload.getState());
    final var updated = applicationMapper.map(applicationRepository.saveAndFlush(
      applicationMapper.update(application, payload)));
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
    if (params.getJobPosition() == null && params.getApplicant() == null && params.getCompany() == null) {
      throw new AccessDeniedException("Access denied: not allowed to browse through all applications");
    }
    if (user.getScope().equals(JobPortalScope.COMPANY) &&
      (params.getApplicant() != null ||
        (params.getCompany() != null && !params.getCompany().equals(user.getCompany().getId())) ||
        (params.getJobPosition() != null && user.getCompany().getJobPositions().stream()
          .noneMatch(jp -> jp.getId().equals(params.getJobPosition()))))) {
      throw new AccessDeniedException(
        "Access denied: Company is not allowed to filter by another company, another companies' jobs, or see all applications of users");
    }
    if (user.getScope().equals(JobPortalScope.REGULAR_USER) && params.getApplicant() == null) {
      throw new AccessDeniedException(
        "Access denied: Applicant is not allowed to see another applicants' applications");
    }
  }
}
