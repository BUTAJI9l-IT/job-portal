package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.mapper.ApplicationMapper;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
import muni.fi.cz.jobportal.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;

  @NonNull
  @Override
  public ApplicationDto create(@NonNull ApplicationCreateDto payload) {
    return applicationMapper.map(applicationRepository.saveAndFlush(applicationMapper.map(payload)));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public ApplicationDto findOne(@NonNull UUID id) {
    return applicationMapper.map(applicationRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<ApplicationDto> findAll(Pageable pageable, ApplicationQueryParams params) {
    return applicationRepository.search(pageable, params).map(applicationMapper::map);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.canManageApplication(#id)")
  public ApplicationDto update(@NonNull UUID id, @NonNull ApplicationUpdateDto payload) {
    final var updated = applicationMapper.map(applicationRepository.saveAndFlush(
      applicationMapper.update(applicationRepository.getOneByIdOrThrowNotFound(id), payload)));
    // TODO SEND NOTIFICATION TO USER ABOUT CHANGING A STATE OF APPLICATON
    // notifyStateChangedTo(payload.getState());
    return updated;
  }

  @Override
  @PreAuthorize("@authorityValidator.canDeleteApplication(#id)")
  public void delete(@NonNull UUID id) {
    applicationRepository.delete(applicationRepository.getOneByIdOrThrowNotFound(id));
  }
}
