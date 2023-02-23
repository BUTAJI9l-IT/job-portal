package muni.fi.cz.jobportal.api.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationCreateDto {

  @NotNull
  private UUID job;
  @NotNull
  private UUID applicant;
}
