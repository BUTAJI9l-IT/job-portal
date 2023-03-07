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
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    return convertToPDF(parseTemplate(Map.of(
      NAME, applicant.getUser().getName(),
      LAST_NAME, applicant.getUser().getLastName(),
      AVATAR, fileService.getAvatar(applicant.getUser().getId()).getAvatar(),
      EMAIL, applicant.getUser().getEmail(),
      PHONE, applicant.getPhone(),
      PROFILE, applicant.getProfile(),
      EXPERIENCES, experienceMapper.map(applicant.getExperiences())
    ), CV_TEMPLATE_NAME));
  }

  @NonNull
  @Override
  public String parseTemplate(@NonNull Map<TemplateParameter, Object> variables, @NonNull String template) {
    final var context = new Context();
    context.setVariables(variables.entrySet().stream()
      .collect(Collectors.toMap(e -> e.getKey().toString(), Entry::getValue)));
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
}
