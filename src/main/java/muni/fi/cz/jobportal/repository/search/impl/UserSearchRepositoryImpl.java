package muni.fi.cz.jobportal.repository.search.impl;

import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.domain.User;
import muni.fi.cz.jobportal.repository.search.UserSearchRepository;

public class UserSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<User, UserQueryParams> implements
  UserSearchRepository {

  @Override
  public Class<User> getBaseClass() {
    return User.class;
  }
}
