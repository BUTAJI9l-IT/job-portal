package com.github.butaji9l.jobportal.be.mapper;

import com.github.butaji9l.jobportal.be.api.common.RegistrationRequest;
import com.github.butaji9l.jobportal.be.api.common.UserDto;
import com.github.butaji9l.jobportal.be.api.request.UserCreateDto;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.factory.UserFactory;
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

  @Mapping(target = "nui", source = "NUI")
  UserDto map(User source);

  UserCreateDto map(RegistrationRequest request);

}
