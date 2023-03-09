package muni.fi.cz.jobportal.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import muni.fi.cz.jobportal.configuration.properties.JobPortalApplicationProperties;
import muni.fi.cz.jobportal.email.IEmail;
import muni.fi.cz.jobportal.enums.TemplateParameter;
import muni.fi.cz.jobportal.exception.EmailCreateException;
import muni.fi.cz.jobportal.service.EmailService;
import muni.fi.cz.jobportal.service.ThymeleafService;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * {@link EmailService} Implementation
 *
 * @author Vitalii Bortsov
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final ResourceBundleMessageSource messageSource;
  private final JavaMailSender javaMailSender;
  private final MailProperties mailProperties;
  private final ThymeleafService thymeleafService;
  private final JobPortalApplicationProperties properties;

  @Async
  @Override
  public void sendEmail(@NonNull IEmail email) {
    if (properties.getNotifications().getEnabled().equals(Boolean.TRUE)) {
      final var subject = messageSource.getMessage("subject." + email.getTemplateName(), new Object[0], Locale.ENGLISH);
      final var message = javaMailSender.createMimeMessage();
      final var body = thymeleafService.parseTemplate(getContextVars(email), email.getTemplateName());
      final MimeMessageHelper helper;
      try {
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailProperties.getUsername(), "Job Portal");
        helper.setTo(email.getRecipient());
        helper.setSubject(subject);
        helper.setText(body, true);
      } catch (MessagingException | UnsupportedEncodingException ignored) {
        throw new EmailCreateException();
      }
      javaMailSender.send(message);
    }
  }

  private Map<TemplateParameter, Object> getContextVars(IEmail email) {
    final var variables = email.getContextVariables();
    variables.put(TemplateParameter.URI, properties.getBaseUri());
    return variables;
  }
}
