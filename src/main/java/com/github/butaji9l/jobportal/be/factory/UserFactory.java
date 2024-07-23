package com.github.butaji9l.jobportal.be.factory;

import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.mapper.PreferencesMapper;
import lombok.RequiredArgsConstructor;
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
