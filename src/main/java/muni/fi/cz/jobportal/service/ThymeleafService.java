package muni.fi.cz.jobportal.service;

import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import muni.fi.cz.jobportal.enums.TemplateParameter;
import org.springframework.lang.NonNull;

public interface ThymeleafService {

  /**
   * Generates a CV file for given applicant.
   *
   * @param applicantId id of an applicant
   * @return byte array of the file
   */
  @NonNull
  ByteArrayInputStream generateCvPdf(@NonNull UUID applicantId);

  /**
   * Parses HTML template with Thymeleaf.
   *
   * @param variables template variables
   * @param template  template name
   * @return parsed html
   */
  @NonNull
  String parseTemplate(@NonNull Map<TemplateParameter, Object> variables, @NonNull String template,
    @NonNull Locale lang);
}
