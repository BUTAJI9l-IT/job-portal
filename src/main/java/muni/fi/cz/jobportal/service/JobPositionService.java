package muni.fi.cz.jobportal.service;

import java.util.UUID;
import muni.fi.cz.jobportal.api.common.FavouritesJobsResponse;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
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
