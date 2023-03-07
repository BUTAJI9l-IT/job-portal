package muni.fi.cz.jobportal.service;

import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.UUID;
import muni.fi.cz.jobportal.enums.TemplateParameter;
import org.springframework.lang.NonNull;

public interface ThymeleafService {

  @NonNull
  ByteArrayInputStream generateCvPdf(@NonNull UUID applicantId);

  @NonNull
  String parseTemplate(@NonNull Map<TemplateParameter, Object> variables, @NonNull String template);
}
