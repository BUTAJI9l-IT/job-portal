package muni.fi.cz.jobportal.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

@JobPortalService
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  @NonNull
  @Override
  public UserDto create(@NonNull UserCreateDto payload) {
    return UserService.super.create(payload);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public UserDto findOne(@NonNull UUID id) {
    return UserService.super.findOne(id);
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> findAll(Pageable pageable, UserQueryParams params) {
    return UserService.super.findAll(pageable, params);
  }

  @NonNull
  @Override
  public UserDto update(@NonNull UUID id, @NonNull UserUpdateDto payload) {
    return UserService.super.update(id, payload);
  }

  @Override
  public void delete(@NonNull UUID id) {
    UserService.super.delete(id);
  }
}
