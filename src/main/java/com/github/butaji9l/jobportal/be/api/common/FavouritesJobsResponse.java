package com.github.butaji9l.jobportal.be.api.common;

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
