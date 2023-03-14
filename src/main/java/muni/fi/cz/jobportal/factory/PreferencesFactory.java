package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.UserPreferences;
import muni.fi.cz.jobportal.repository.LanguageRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * Object factory for preferences
 *
 * @author Vitalii Bortsov
 */
@Component
@RequiredArgsConstructor
public class PreferencesFactory {

  private final LanguageRepository languageRepository;

  @ObjectFactory
  public UserPreferences prepare(UserCreateDto request) {
    final var preferences = new UserPreferences();
    preferences.setLanguage(languageRepository.getOneByIdOrDefault(request.getLanguage()));
    return preferences;
  }
}
