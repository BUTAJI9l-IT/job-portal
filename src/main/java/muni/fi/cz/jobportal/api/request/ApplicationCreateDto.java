package muni.fi.cz.jobportal.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCreateDto {

  @NotNull
  private UUID job;
  @NotNull
  private UUID applicant;
}
