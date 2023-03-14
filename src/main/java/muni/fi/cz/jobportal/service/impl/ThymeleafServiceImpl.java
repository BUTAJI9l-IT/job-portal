package muni.fi.cz.jobportal.service.impl;

import static muni.fi.cz.jobportal.enums.TemplateParameter.AVATAR;
import static muni.fi.cz.jobportal.enums.TemplateParameter.EMAIL;
import static muni.fi.cz.jobportal.enums.TemplateParameter.EXPERIENCES;
import static muni.fi.cz.jobportal.enums.TemplateParameter.LAST_NAME;
import static muni.fi.cz.jobportal.enums.TemplateParameter.NAME;
import static muni.fi.cz.jobportal.enums.TemplateParameter.PHONE;
import static muni.fi.cz.jobportal.enums.TemplateParameter.PROFILE;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.enums.TemplateParameter;
import muni.fi.cz.jobportal.exception.ConversionException;
import muni.fi.cz.jobportal.mapper.ExperienceMapper;
import muni.fi.cz.jobportal.repository.ApplicantRepository;
import muni.fi.cz.jobportal.service.FileService;
import muni.fi.cz.jobportal.service.ThymeleafService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * {@link ThymeleafService} Implementation
 *
 * @author Vitalii Bortsov
 */
@Service
@RequiredArgsConstructor
public class ThymeleafServiceImpl implements ThymeleafService {

  public static final String CV_TEMPLATE_NAME = "cv.html";
  private final ApplicantRepository applicantRepository;
  private final ExperienceMapper experienceMapper;
  private final TemplateEngine templateEngine;
  private final ConverterProperties converterProperties;
  private final FileService fileService;

  @NonNull
  @Override
  public ByteArrayInputStream generateCvPdf(@NonNull UUID applicantId) {
    final var applicant = applicantRepository.getOneByIdOrThrowNotFound(applicantId);
    try {
      return convertToPDF(parseTemplate(Map.of(
        NAME, safeGetString(applicant.getUser().getName()),
        LAST_NAME, safeGetString(applicant.getUser().getLastName()),
        AVATAR,
        Base64Utils.encodeToString(fileService.getAvatar(applicant.getUser().getId()).resource().getInputStream()
          .readAllBytes()),
        EMAIL, safeGetString(applicant.getUser().getEmail()),
        PHONE, safeGetString(applicant.getPhone()),
        PROFILE, safeGetString(applicant.getProfile()),
        EXPERIENCES, experienceMapper.map(applicant.getExperiences() != null ? applicant.getExperiences() : List.of())
      ), CV_TEMPLATE_NAME, Locale.ENGLISH));
    } catch (IOException e) {
      throw new ConversionException();
    }
  }

  @NonNull
  @Override
  public String parseTemplate(@NonNull Map<TemplateParameter, Object> variables, @NonNull String template,
    @NonNull Locale lang) {
    final var context = new Context();
    context.setVariables(variables.entrySet().stream()
      .collect(Collectors.toMap(e -> e.getKey().toString(), Entry::getValue)));
    context.setLocale(lang);
    return templateEngine.process(template, context);
  }

  @NonNull
  private ByteArrayInputStream convertToPDF(String parsed) {
    try {
      final var out = new ByteArrayOutputStream();
      HtmlConverter.convertToPdf(parsed, out, converterProperties);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (Exception ignore) {
      throw new ConversionException();
    }
  }


  @NonNull
  private String safeGetString(@Nullable String s) {
    return s == null ? "" : s;
  }
}
