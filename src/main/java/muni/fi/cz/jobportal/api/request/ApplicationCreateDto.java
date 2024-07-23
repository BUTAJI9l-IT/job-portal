package muni.fi.cz.jobportal.api.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
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
