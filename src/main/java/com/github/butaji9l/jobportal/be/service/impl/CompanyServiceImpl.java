package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.CompanyDto;
import com.github.butaji9l.jobportal.be.api.detail.CompanyDetailDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyCreateDto;
import com.github.butaji9l.jobportal.be.api.request.CompanyUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.CompanyQueryParams;
import com.github.butaji9l.jobportal.be.mapper.CompanyMapper;
import com.github.butaji9l.jobportal.be.repository.CompanyRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.CompanyService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link CompanyService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
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
    userRepository.saveAndFlush(companyMapper.update(company, payload).getUser());
    return companyMapper.map(company);
  }

  @Override
  public void delete(@NonNull UUID id) {
    companyRepository.delete(companyRepository.getOneByIdOrThrowNotFound(id));
  }
}
