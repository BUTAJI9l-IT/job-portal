package muni.fi.cz.jobportal.service.impl;

import static muni.fi.cz.jobportal.utils.AuthenticationUtils.getCurrentUser;

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
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.exception.JobPositionAlreadySavedException;
import muni.fi.cz.jobportal.exception.JobPositionNotInSavedException;
import muni.fi.cz.jobportal.exception.PositionIsNotActiveException;
import muni.fi.cz.jobportal.mapper.JobPositionMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.repository.JobCategoryRepository;
import muni.fi.cz.jobportal.repository.JobPositionRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.ApplicationService;
import muni.fi.cz.jobportal.service.JobPositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {

  private final ApplicationService applicationService;
  private final JobPositionRepository jobPositionRepository;
  private final UserRepository userRepository;
  private final JobPositionMapper jobPositionMapper;
  private final ApplicantRepository applicantRepository;
  private final JobCategoryRepository jobCategoryRepository;

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.canCreateJobPosition(#payload)")
  public JobPositionDetailDto create(@NonNull JobPositionCreateDto payload) {
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
    return jobPositionRepository.search(pageable, params).map(jobPositionMapper::map);
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.jobBelongsToCompany(#id)")
  public JobPositionDetailDto update(@NonNull UUID id, @NonNull JobPositionUpdateDto payload) {
    final var updated = jobPositionMapper.update(jobPositionRepository.getOneByIdOrThrowNotFound(id), payload);
    updated.setJobCategories(jobCategoryRepository.findAllById(payload.getJobCategories()));
    return jobPositionMapper.map(jobPositionRepository.saveAndFlush(updated));
  }

  @Override
  @PreAuthorize("@authorityValidator.jobBelongsToCompany(#id)")
  public void delete(@NonNull UUID id) {
    jobPositionRepository.delete(jobPositionRepository.getOneByIdOrThrowNotFound(id));
  }

  @NonNull
  @Override
  @PreAuthorize("hasAuthority('SCOPE_ADMIN') OR hasAuthority('SCOPE_REGULAR_USER')")
  public JobPositionDetailDto apply(@NonNull UUID jobPositionId) {
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (jobPosition.getStatus() == PositionState.INACTIVE) {
      throw new PositionIsNotActiveException();
    }
    final var applicant = userRepository.getOneByIdOrThrowNotFound(getCurrentUser()).getApplicant();
    applicationService.create(ApplicationCreateDto.builder().job(jobPositionId).applicant(applicant.getId()).build());
    return jobPositionMapper.map(jobPositionRepository.saveAndFlush(jobPosition));
  }

  @NonNull
  @Override
  @PreAuthorize("hasAuthority('SCOPE_ADMIN') OR hasAuthority('SCOPE_REGULAR_USER')")
  public FavouritesJobsResponse getFavorites() {
    return new FavouritesJobsResponse(
      userRepository.getOneByIdOrThrowNotFound(getCurrentUser()).getApplicant().getSavedJobs()
        .stream()
        .map(jp -> (JobPositionDto) jobPositionMapper.map(jp))
        .toList()
    );
  }

  @NonNull
  @Override
  @PreAuthorize("hasAuthority('SCOPE_ADMIN') OR hasAuthority('SCOPE_REGULAR_USER')")
  public FavouritesJobsResponse addToFavorites(@NonNull UUID jobPositionId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(getCurrentUser());
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (jobPositionRepository.userWithIdLiked(jobPosition, user.getId())) {
      throw new JobPositionAlreadySavedException();
    }
    user.getApplicant().getSavedJobs().add(jobPosition);
    applicantRepository.saveAndFlush(user.getApplicant());
    return new FavouritesJobsResponse(user.getApplicant().getSavedJobs()
      .stream()
      .map(jp -> (JobPositionDto) jobPositionMapper.map(jp))
      .toList());
  }

  @NonNull
  @Override
  @PreAuthorize("hasAuthority('SCOPE_ADMIN') OR hasAuthority('SCOPE_REGULAR_USER')")
  public FavouritesJobsResponse removeFromFavorites(@NonNull UUID jobPositionId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(getCurrentUser());
    final var jobPosition = jobPositionRepository.getOneByIdOrThrowNotFound(jobPositionId);
    if (!jobPositionRepository.userWithIdLiked(jobPosition, user.getId())) {
      throw new JobPositionNotInSavedException();
    }
    user.getApplicant().getSavedJobs().removeIf(jp -> jp.getId().equals(jobPosition.getId()));
    applicantRepository.saveAndFlush(user.getApplicant());
    return new FavouritesJobsResponse(user.getApplicant().getSavedJobs()
      .stream()
      .map(jp -> (JobPositionDto) jobPositionMapper.map(jp))
      .toList());
  }
}
