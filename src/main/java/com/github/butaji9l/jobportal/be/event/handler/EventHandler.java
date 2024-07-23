package com.github.butaji9l.jobportal.be.event.handler;

import com.github.butaji9l.jobportal.be.api.email.ApplicationEmailDto;
import com.github.butaji9l.jobportal.be.configuration.properties.JobPortalApplicationProperties;
import com.github.butaji9l.jobportal.be.email.ApplicationStateChangedEmail;
import com.github.butaji9l.jobportal.be.event.ApplicationStateChangedEvent;
import com.github.butaji9l.jobportal.be.repository.ApplicationRepository;
import com.github.butaji9l.jobportal.be.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class with application event handlers.
 *
 * @author Vitalii Bortsov
 */
@Component
@Transactional
@RequiredArgsConstructor
public class EventHandler {

  private final ApplicationRepository applicationRepository;
  private final EmailService emailService;
  private final JobPortalApplicationProperties properties;

  @EventListener
  public void handleApplicationStateChange(
    ApplicationStateChangedEvent applicationStateChangedEvent) {
    final var application = applicationRepository.getOneByIdOrThrowNotFound(
      applicationStateChangedEvent.getApplication());
    if (application.getApplicant().getUser().getPreferences().getNotificationsEnabled()
      .equals(Boolean.TRUE)
      && (properties.getNotifications().getEnabled().equals(Boolean.TRUE))) {
      final var email = new ApplicationStateChangedEmail(
        ApplicationEmailDto.builder()
          .state(applicationStateChangedEvent.getStatus())
          .jobPosition(application.getJobPosition().getPositionName())
          .company(application.getJobPosition().getCompany().getCompanyName())
          .recipient(application.getApplicant().getUser().getEmail())
          .build());
      emailService.sendEmail(email,
        application.getApplicant().getUser().getPreferences().getLanguage().toLocale());
    }
  }
}
