package muni.fi.cz.jobportal.api.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouritesJobsResponse {

  private List<JobPositionDto> jobs;
}
