package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.Language;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;

public interface LanguageRepository extends JobPortalRepository<Language, String> {

  default Language getOneByIdOrDefault(String code) {
    return findById(code).orElseGet(() -> findById("en").orElseThrow(() -> {
      throw new EntityNotFoundException(Language.class);
    }));
  }

  @Override
  default Class<Language> getBaseClass() {
    return Language.class;
  }

}
