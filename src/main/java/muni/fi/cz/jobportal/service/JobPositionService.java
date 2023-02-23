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

  @NonNull
  JobPositionDetailDto apply(@NonNull UUID jobPositionId);

  @NonNull
  FavouritesJobsResponse getFavorites();

  @NonNull
  FavouritesJobsResponse addToFavorites(@NonNull UUID jobPositionId);

  @NonNull
  FavouritesJobsResponse removeFromFavorites(@NonNull UUID jobPositionId);
}
