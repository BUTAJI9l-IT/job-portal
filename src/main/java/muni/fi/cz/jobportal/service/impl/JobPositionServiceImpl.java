package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
import muni.fi.cz.jobportal.mapper.JobPositionMapper;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.service.JobPositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {

  private final JobPositionRepository jobPositionRepository;
  private final JobPositionMapper jobPositionMapper;

  @NonNull
  @Override
  public JobPositionDetailDto create(@NonNull JobPositionCreateDto payload) {
    return JobPositionService.super.create(payload);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public JobPositionDetailDto findOne(@NonNull UUID id) {
    return jobPositionMapper.map(jobPositionRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<JobPositionDto> findAll(Pageable pageable, JobPositionQueryParams params) {
    return JobPositionService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public JobPositionDetailDto update(@NonNull UUID id, @NonNull JobPositionUpdateDto payload) {
    return JobPositionService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    JobPositionService.super.delete(id);
  }

  @NonNull
  @Override
  public JobPositionDetailDto apply(@NonNull UUID jobPositionId) {
    throw new UnsupportedOperationException();
  }
}
