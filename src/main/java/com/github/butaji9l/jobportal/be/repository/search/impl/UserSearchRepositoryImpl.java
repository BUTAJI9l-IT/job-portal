package com.github.butaji9l.jobportal.be.repository.search.impl;

import com.github.butaji9l.jobportal.be.api.search.UserQueryParams;
import com.github.butaji9l.jobportal.be.domain.User;
import com.github.butaji9l.jobportal.be.repository.search.UserSearchRepository;

public class UserSearchRepositoryImpl extends
  AbstractJobPortalSearchRepository<User, UserQueryParams> implements
  UserSearchRepository {

  @Override
  public Class<User> getBaseClass() {
    return User.class;
  }
}
