package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.UserPreferences;

import java.util.UUID;

public interface PreferencesRepository extends JobPortalRepository<UserPreferences, UUID> {

  @Override
  default Class<UserPreferences> getBaseClass() {
    return UserPreferences.class;
  }

}
