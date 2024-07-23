package com.github.butaji9l.jobportal.be.api.common;

import com.neovisionaries.i18n.LanguageCode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferencesDto {

  @NotNull
  private LanguageCode language;

  @NotNull
  private Boolean notificationsEnabled;

}
