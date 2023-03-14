package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.PreferencesDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.UserPreferences;
import muni.fi.cz.jobportal.factory.PreferencesFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper for preferences
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = PreferencesFactory.class)
public interface PreferencesMapper {

  @Mapping(target = "notificationsEnabled", constant = "false")
  @Mapping(target = "language", ignore = true)
  UserPreferences mapPreferences(UserCreateDto createDto);

  @Mapping(target = "language", source = "language.code")
  PreferencesDto map(UserPreferences source);

  @Mapping(target = "language", ignore = true)
  void update(@MappingTarget UserPreferences target, PreferencesDto source);
}
