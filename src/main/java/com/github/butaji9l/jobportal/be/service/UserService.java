package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.PreferencesDto;
import com.github.butaji9l.jobportal.be.api.common.UserDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.api.request.UserUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.UserQueryParams;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface UserService extends
  CRUDService<UserCreateDto, UserDto, UserUpdateDto, UserDto, UserQueryParams> {

  @NonNull
  PreferencesDto updatePreferences(@NonNull UUID userId, @NonNull PreferencesDto payload);

  @NonNull
  PreferencesDto getUserPreferences(@NonNull UUID userId);
}
