package muni.fi.cz.jobportal.api.request;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantCreateDto {

  @NotNull
  private UUID user;
}
