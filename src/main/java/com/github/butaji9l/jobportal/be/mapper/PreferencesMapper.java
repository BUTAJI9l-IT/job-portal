package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.PreferencesDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.UserPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for preferences
 *
 * @author Vitalii Bortsov
 */
@Mapper
public interface PreferencesMapper {

  @Mapping(target = "notificationsEnabled", constant = "false")
  UserPreferences mapPreferences(UserCreateDto createDto);

  PreferencesDto map(UserPreferences source);

  void update(@MappingTarget UserPreferences target, PreferencesDto source);
}
