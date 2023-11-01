package muni.fi.cz.jobportal.api.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PreferencesDto {

  @NotNull
  @NotEmpty
  private String language;

  @NotNull
  private Boolean notificationsEnabled;

}
