package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.FavouritesJobsResponse;
import com.github.butaji9l.jobportal.be.api.common.JobPositionDto;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.JobPositionQueryParams;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.exception.ApplicationAlreadyExistsException;
import com.github.butaji9l.jobportal.be.exception.JobPositionAlreadySavedException;
import com.github.butaji9l.jobportal.be.exception.JobPositionNotInSavedException;
import com.github.butaji9l.jobportal.be.exception.PositionIsNotActiveException;
import com.github.butaji9l.jobportal.be.mapper.JobPositionMapper;
import com.github.butaji9l.jobportal.be.repository.ApplicantRepository;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.repository.JobCategoryRepository;
import com.github.butaji9l.jobportal.be.repository.JobPositionRepository;
import com.github.butaji9l.jobportal.be.service.ApplicationService;
import com.github.butaji9l.jobportal.be.service.JobPositionService;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link JobPositionService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {

  private final ApplicationService applicationService;
  private final JobPositionRepository jobPositionRepository;
  private final JobPositionMapper jobPositionMapper;
  private final ApplicantRepository applicantRepository;
  private final JobCategoryRepository jobCategoryRepository;
  private final ApplicationRepository applicationRepository;
  private final StaticObjectFactory staticObjectFactory;

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.canCreateJobPosition(#payload)")
  public JobPositionDetailDto create(@NonNull JobPositionCreateDto payload) {
    jobCategoryRepository.findByOccupationName(payload.getPositionName())
      .ifPresent(cat -> {
        if (payload.getJobCategories() == null) {
          payload.setJobCategories(List.of(cat.getId()));
        } else if (payload.getJobCategories().stream().noneMatch(id -> id.equals(cat.getId()))) {
          payload.getJobCategories().add(cat.getId());
        }
      });
    return jobPositionMapper.map(jobPositionRepository.save(jobPositionMapper.map(payload)));
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
    return jobPositionRepository.search(pageable, params).map(jobPositionMapper::mapDto);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.jobBelongsToCompany(#id)")
  public JobPositionDetailDto update(@NonNull UUID id, @NonNull JobPositionUpdateDto payload) {
    final var updated = jobPositionMapper.update(
      jobPositionRepository.getOneByIdOrThrowNotFound(id), payload);
    if (payload.getStatus().equals(PositionState.INACTIVE)) {
      updated.getApplications().forEach(application -> {
        application.setState(ApplicationState.CLOSED);
        applicationRepository.saveAndFlush(application);
      });
    }
    updated.setJobCategories(jobCategoryRepository.findAllById(payload.getJobCategories()));
    updated.setLastUpdated(staticObjectFactory.getNowAsInstant());
    return jobPositionMapper.map(jobPositionRepository.saveAndFlush(updated));
  }

  @Override
  @PreAuthorize("@authorityValidator.jobBelongsToCompany(#id)")
  public void delete(@NonNull UUID id) {
    jobPositionRepository.delete(jobPositionRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() OR @authorityValidator.isCurrentApplicant(#applicantId)")
  public JobPositionDetailDto apply(@NonNull UUID applicantId, @NonNull UUID jobPositionId) {
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (jobPosition.getStatus() == PositionState.INACTIVE) {
      throw new PositionIsNotActiveException();
    }
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(applicantId);
    if (applicationRepository.existsActive(applicant.getId(), jobPositionId)) {
      throw new ApplicationAlreadyExistsException();
    }
    applicationService.create(
      ApplicationCreateDto.builder().job(jobPositionId).applicant(applicant.getId()).build());
    return jobPositionMapper.map(jobPositionRepository.saveAndFlush(jobPosition));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() OR @authorityValidator.isCurrentApplicant(#applicantId)")
  public FavouritesJobsResponse getFavorites(@NonNull UUID applicantId) {
    return new FavouritesJobsResponse(
      applicantRepository.getOneByIdOrThrowNotFound(applicantId).getSavedJobs()
        .stream()
        .map(jobPositionMapper::mapDto)
        .toList()
    );
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() OR @authorityValidator.isCurrentApplicant(#applicantId)")
  public FavouritesJobsResponse addToFavorites(@NonNull UUID applicantId,
    @NonNull UUID jobPositionId) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(applicantId);
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (jobPositionRepository.applicantWithIdLiked(jobPosition, applicant.getId())) {
      throw new JobPositionAlreadySavedException();
    }
    applicant.getSavedJobs().add(jobPosition);
    applicantRepository.saveAndFlush(applicant);
    return new FavouritesJobsResponse(applicant.getSavedJobs()
      .stream()
      .map(jobPositionMapper::mapDto)
      .toList());
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() OR @authorityValidator.isCurrentApplicant(#applicantId)")
  public FavouritesJobsResponse removeFromFavorites(@NonNull UUID applicantId,
    @NonNull UUID jobPositionId) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(applicantId);
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (!jobPositionRepository.applicantWithIdLiked(jobPosition, applicant.getId())) {
      throw new JobPositionNotInSavedException();
    }
    applicant.getSavedJobs().removeIf(jp -> jp.getId().equals(jobPosition.getId()));
    applicantRepository.saveAndFlush(applicant);
    return new FavouritesJobsResponse(applicant.getSavedJobs()
      .stream()
      .map(jobPositionMapper::mapDto)
      .toList());
  }
}
