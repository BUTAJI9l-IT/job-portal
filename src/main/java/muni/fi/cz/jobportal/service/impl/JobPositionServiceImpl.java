package muni.fi.cz.jobportal.service.impl;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.FavouritesJobsResponse;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
import muni.fi.cz.jobportal.enums.ApplicationState;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.exception.ApplicationAlreadyExistsException;
import muni.fi.cz.jobportal.exception.JobPositionAlreadySavedException;
import muni.fi.cz.jobportal.exception.JobPositionNotInSavedException;
import muni.fi.cz.jobportal.exception.PositionIsNotActiveException;
import muni.fi.cz.jobportal.mapper.JobPositionMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.ApplicationRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.service.ApplicationService;
import muni.fi.cz.jobportal.service.JobPositionService;
import muni.fi.cz.jobportal.utils.StaticObjectFactory;
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
    final var updated = jobPositionMapper.update(jobPositionRepository.getOneByIdOrThrowNotFound(id), payload);
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
    applicationService.create(ApplicationCreateDto.builder().job(jobPositionId).applicant(applicant.getId()).build());
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
  public FavouritesJobsResponse addToFavorites(@NonNull UUID applicantId, @NonNull UUID jobPositionId) {
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
  public FavouritesJobsResponse removeFromFavorites(@NonNull UUID applicantId, @NonNull UUID jobPositionId) {
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
