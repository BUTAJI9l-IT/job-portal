package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserCreateDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;

public interface UserService extends CRUDService<UserCreateDto, UserDto, UserUpdateDto, UserDto, UserQueryParams> {

}
