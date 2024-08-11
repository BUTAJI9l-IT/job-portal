package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.FavouritesJobsResponse;
import com.github.butaji9l.jobportal.be.api.common.JobPositionDto;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.JobPositionQueryParams;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface JobPositionService extends
  CRUDService<JobPositionCreateDto, JobPositionDto, JobPositionUpdateDto, JobPositionDetailDto, JobPositionQueryParams> {

  /**
   * Creates a new application.
   *
   * @param applicantId   id of applicant
   * @param jobPositionId id of job position
   * @return updated detail of job position
   */
  @NonNull
  JobPositionDetailDto apply(@NonNull UUID applicantId, @NonNull UUID jobPositionId);

  /**
   * Returns favorites job positions of given applicant.
   *
   * @param applicantId id of applicant
   * @return job positions liked by applicant
   */
  @NonNull
  FavouritesJobsResponse getFavorites(@NonNull UUID applicantId);

  /**
   * Adds a job position to favorites job positions of given applicant.
   *
   * @param applicantId   id of applicant
   * @param jobPositionId id of job position
   * @return updated list of favorites job positions for given applicant
   */
  @NonNull
  FavouritesJobsResponse addToFavorites(@NonNull UUID applicantId, @NonNull UUID jobPositionId);

  /**
   * Removes a job position from favorites job positions of given applicant.
   *
   * @param applicantId   id of applicant
   * @param jobPositionId id of job position
   * @return updated list of favorites job positions for given applicant
   */
  @NonNull
  FavouritesJobsResponse removeFromFavorites(@NonNull UUID applicantId,
    @NonNull UUID jobPositionId);
}
