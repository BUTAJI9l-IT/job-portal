package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.UserPreferences;

public interface PreferencesRepository extends JobPortalRepository<UserPreferences, UUID> {

  @Override
  default Class<UserPreferences> getBaseClass() {
    return UserPreferences.class;
  }

}
