package muni.fi.cz.jobportal.repository;

import muni.fi.cz.jobportal.domain.File;

import java.util.UUID;

public interface FileRepository extends JobPortalRepository<File, UUID> {

  @Override
  default Class<File> getBaseClass() {
    return File.class;
  }
}
