package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.PreferencesDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.UserPreferences;
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
