package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import muni.fi.cz.jobportal.mapper.ApplicantMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.service.ApplicantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

  private final ApplicantRepository applicantRepository;
  private final ApplicantMapper applicantMapper;

  @NonNull
  @Override
  public ApplicantDetailDto create(@NonNull ApplicantCreateDto payload) {
    return applicantMapper.map(applicantRepository.save(applicantMapper.map(payload)));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public ApplicantDetailDto findOne(@NonNull UUID id) {
    return applicantMapper.map(applicantRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<ApplicantDto> findAll(Pageable pageable, ApplicantQueryParams params) {
    return ApplicantService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public ApplicantDetailDto update(@NonNull UUID id, @NonNull ApplicantUpdateDto payload) {
    return ApplicantService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    ApplicantService.super.delete(id);
  }
}
