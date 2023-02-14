package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyCreateDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.search.CompanyQueryParams;
import muni.fi.cz.jobportal.mapper.CompanyMapper;
import muni.fi.cz.jobportal.repository.CompanyRepository;
import muni.fi.cz.jobportal.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyMapper companyMapper;

  @NonNull
  @Override
  public CompanyDetailDto create(@NonNull CompanyCreateDto payload) {
    return companyMapper.map(companyRepository.save(companyMapper.map(payload)));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public CompanyDetailDto findOne(@NonNull UUID id) {
    return CompanyService.super.findOne(id);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<CompanyDto> findAll(Pageable pageable, CompanyQueryParams params) {
    return CompanyService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public CompanyDetailDto update(@NonNull UUID id, @NonNull CompanyUpdateDto payload) {
    return CompanyService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    CompanyService.super.delete(id);
  }
}
