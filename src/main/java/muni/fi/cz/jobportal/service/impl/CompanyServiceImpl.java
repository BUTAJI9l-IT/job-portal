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
import org.springframework.security.access.prepost.PreAuthorize;
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
    return companyMapper.map(companyRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<CompanyDto> findAll(Pageable pageable, CompanyQueryParams params) {
    return companyRepository.search(pageable, params).map(companyMapper::mapDto);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentCompany(#id)")
  public CompanyDetailDto update(@NonNull UUID id, @NonNull CompanyUpdateDto payload) {
    final var company = companyRepository.getOneByIdOrThrowNotFound(id);
    companyRepository.saveAndFlush(companyMapper.update(company, payload));
    return companyMapper.map(company);
  }

  @Override
  public void delete(@NonNull UUID id) {
    companyRepository.delete(companyRepository.getOneByIdOrThrowNotFound(id));
  }
}
