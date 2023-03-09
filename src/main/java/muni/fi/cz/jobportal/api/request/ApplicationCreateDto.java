package muni.fi.cz.jobportal.api.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
