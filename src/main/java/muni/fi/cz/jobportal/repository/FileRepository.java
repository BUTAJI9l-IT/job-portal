package muni.fi.cz.jobportal.repository;

import java.util.UUID;
import muni.fi.cz.jobportal.domain.File;

public interface FileRepository extends JobPortalRepository<File, UUID> {

  @Override
  default Class<File> getBaseClass() {
    return File.class;
  }
}
