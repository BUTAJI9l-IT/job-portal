package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.factory.UserFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for users
 *
 * @author Vitalii Bortsov
 */
@Mapper(uses = UserFactory.class)
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User map(UserCreateDto request);

  UserDto map(User source);

  UserCreateDto map(RegistrationRequest request);

}
