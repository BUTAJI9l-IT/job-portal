package muni.fi.cz.jobportal.api.common;

import java.util.List;
import lombok.Data;

@Data
public class FavouritesJobsResponse {

  private List<JobPositionDto> jobs;
}
