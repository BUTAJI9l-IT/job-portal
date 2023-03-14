package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.email.IEmail;
import org.springframework.lang.NonNull;

public interface EmailService {

  /**
   * Sends an email if notifications are enabled.
   *
   * @param email an email to be sent
   * @param lang  language of an email
   */
  void sendEmail(@NonNull IEmail email, String lang);

}
