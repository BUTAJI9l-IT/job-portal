package muni.fi.cz.jobportal.api.common;

import com.neovisionaries.i18n.LanguageCode;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PreferencesDto {

  @NotNull
  private LanguageCode language;

  @NotNull
  private Boolean notificationsEnabled;

}
