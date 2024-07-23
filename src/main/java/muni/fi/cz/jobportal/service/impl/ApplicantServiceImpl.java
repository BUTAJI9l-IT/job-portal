package muni.fi.cz.jobportal.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import muni.fi.cz.jobportal.domain.Experience;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.mapper.ApplicantMapper;
import muni.fi.cz.jobportal.mapper.ExperienceMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.ExperienceRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.ApplicantService;
import muni.fi.cz.jobportal.service.ThymeleafService;
import muni.fi.cz.jobportal.utils.AuthorityValidator;
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
