package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.UserPreferences;
import java.util.UUID;

public interface PreferencesRepository extends JobPortalRepository<UserPreferences, UUID> {

  @Override
  default Class<UserPreferences> getBaseClass() {
    return UserPreferences.class;
  }

}
