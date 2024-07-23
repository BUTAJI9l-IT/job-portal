package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.ApplicantDto;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicantDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import com.github.butaji9l.jobportal.be.domain.Experience;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.exception.EntityNotFoundException;
import com.github.butaji9l.jobportal.be.mapper.ApplicantMapper;
import com.github.butaji9l.jobportal.be.mapper.ExperienceMapper;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.ExperienceRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.ApplicantService;
import com.github.butaji9l.jobportal.be.service.ThymeleafService;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link ApplicantService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

  private final ThymeleafService thymeleafService;
  private final ApplicantRepository applicantRepository;
  private final UserRepository userRepository;
  private final ApplicantMapper applicantMapper;
  private final ExperienceMapper experienceMapper;
  private final ExperienceRepository experienceRepository;
  private final AuthorityValidator authorityValidator;

  @NonNull
  @Override
  public ApplicantDetailDto create(@NonNull ApplicantCreateDto payload) {
    return applicantMapper.map(applicantRepository.save(applicantMapper.map(payload)));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("!@authorityValidator.isRegularUser() || @authorityValidator.isCurrentApplicant(#id)")
  public ApplicantDetailDto findOne(@NonNull UUID id) {
    return applicantMapper.map(applicantRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCompany()")
  public Page<ApplicantDto> findAll(Pageable pageable, ApplicantQueryParams params) {
    final var user = userRepository.getOneByIdOrThrowNotFound(authorityValidator.getCurrentUser());
    if (user.getScope() != JobPortalScope.ADMIN &&
      (params.getJobPosition() == null ||
        user.getCompany().getJobPositions().stream()
          .noneMatch(jp -> jp.getId().equals(params.getJobPosition())))) {
      throw new AccessDeniedException(
        "Cannot see applicants for position with id: " + params.getJobPosition());
    }
    return applicantRepository.search(pageable, params).map(applicantMapper::mapDto);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentApplicant(#id)")
  public ApplicantDetailDto update(@NonNull UUID id, @NonNull ApplicantUpdateDto payload) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(id);
    userRepository.saveAndFlush(applicantMapper.update(applicant, payload).getUser());
    return applicantMapper.map(applicant);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentApplicant(#id)")
  public ApplicantDetailDto addExperience(@NonNull UUID id, @NonNull ExperienceDto payload) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(id);
    final var experience = experienceRepository.saveAndFlush(
      experienceMapper.create(payload, applicant));
    if (applicant.getExperiences() == null) {
      applicant.setExperiences(new ArrayList<>());
    }
    applicant.getExperiences().add(experience);
    applicantRepository.saveAndFlush(applicant);
    return applicantMapper.map(applicant);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentApplicant(#applicantId)")
  public ApplicantDetailDto removeExperience(@NonNull UUID applicantId,
    @NonNull UUID experienceId) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(applicantId);
    final var experience = experienceRepository.getOneByIdOrThrowNotFound(experienceId);
    if (experience.getApplicant().getId().equals(applicant.getId())) {
      applicant.getExperiences().remove(experience);
      experienceRepository.delete(experience);
    } else {
      throw new EntityNotFoundException(Experience.class);
    }
    return applicantMapper.map(applicant);
  }

  @Override
  public void delete(@NonNull UUID id) {
    applicantRepository.delete(applicantRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentApplicant(#applicantId)")
  public ByteArrayInputStream generateCV(UUID applicantId) {
    return thymeleafService.generateCvPdf(applicantId);
  }
}
