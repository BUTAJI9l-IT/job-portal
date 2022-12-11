package muni.fi.cz.jobportal.mapper;

import muni.fi.cz.jobportal.api.common.RegistrationRequest;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.factory.UserFactory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserFactory.class)
public interface UserMapper {

  @Mapping(target = "password", ignore = true)
  User map(RegistrationRequest request);

}
