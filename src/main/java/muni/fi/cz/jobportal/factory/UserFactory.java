package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.mapper.PreferencesMapper;
import muni.fi.cz.jobportal.repository.PreferencesRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Object factory for users
 *
 * @author Vitalii Bortsov
 */
@Component
@RequiredArgsConstructor
public class UserFactory {

  private final PasswordEncoder encoder;
  private final PreferencesMapper preferencesMapper;

  @ObjectFactory
  public User prepare(UserCreateDto request) {
    final var user = new User();
    user.setPassword(encoder.encode(request.getPassword().getPassword()));
    user.setPreferences(preferencesMapper.mapPreferences(request));
    user.getPreferences().setUser(user);
    return user;
  }
}
