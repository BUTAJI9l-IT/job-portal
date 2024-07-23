package com.github.butaji9l.jobportal.be.repository;

import com.github.butaji9l.jobportal.be.domain.File;
import java.util.UUID;

public interface FileRepository extends JobPortalRepository<File, UUID> {

  @Override
  default Class<File> getBaseClass() {
    return File.class;
  }
}
