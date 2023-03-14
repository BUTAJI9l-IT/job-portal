package muni.fi.cz.jobportal.api.common;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferencesDto {

  @NotNull
  @NotEmpty
  private String language;

  @NotNull
  private Boolean notificationsEnabled;

}
