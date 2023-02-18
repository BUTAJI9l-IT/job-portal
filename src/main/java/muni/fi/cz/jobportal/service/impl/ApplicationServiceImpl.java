package muni.fi.cz.jobportal.service.impl;

import java.io.ByteArrayInputStream;
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
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;

  @NonNull
  @Override
  public ApplicationDto create(@NonNull ApplicationCreateDto payload) {
    return ApplicationService.super.create(payload);
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
    return ApplicationService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public ApplicationDto update(@NonNull UUID id, @NonNull ApplicationUpdateDto payload) {
    return ApplicationService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    ApplicationService.super.delete(id);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public ByteArrayInputStream generateCV() {
    throw new UnsupportedOperationException();
  }
}
