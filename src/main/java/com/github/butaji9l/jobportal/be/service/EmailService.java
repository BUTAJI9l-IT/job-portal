package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.email.IEmail;
import java.util.Locale;
import org.springframework.lang.NonNull;

public interface EmailService {

  /**
   * Sends an email if notifications are enabled.
   *
   * @param email  an email to be sent
   * @param locale language of an email
   */
  void sendEmail(@NonNull IEmail email, Locale locale);

}
