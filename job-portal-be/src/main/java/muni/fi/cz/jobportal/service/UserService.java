package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.PreferencesDto;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import org.springframework.lang.NonNull;

import java.util.UUID;

public interface UserService extends CRUDService<UserCreateDto, UserDto, UserUpdateDto, UserDto, UserQueryParams> {

  @NonNull
  PreferencesDto updatePreferences(@NonNull UUID userId, @NonNull PreferencesDto payload);

  @NonNull
  PreferencesDto getUserPreferences(@NonNull UUID userId);
}
