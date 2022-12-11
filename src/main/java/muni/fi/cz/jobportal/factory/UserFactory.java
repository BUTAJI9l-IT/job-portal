package muni.fi.cz.jobportal.factory;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.domain.User;
import org.mapstruct.ObjectFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

  private final PasswordEncoder encoder;

  @ObjectFactory
  public User prepare(RegistrationRequest request) {
    final var user = new User();
    user.setPassword(encoder.encode(request.getPassword()));
    return user;
  }
}
