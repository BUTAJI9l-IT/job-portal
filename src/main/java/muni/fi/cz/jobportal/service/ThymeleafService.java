package muni.fi.cz.jobportal.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface ThymeleafService {

  @NonNull
  ByteArrayInputStream generateCvPdf(@NonNull UUID applicantId) throws IOException;
}
