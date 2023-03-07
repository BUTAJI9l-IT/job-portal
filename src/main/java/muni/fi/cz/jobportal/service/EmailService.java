package muni.fi.cz.jobportal.service;

import muni.fi.cz.jobportal.email.IEmail;
import org.springframework.lang.NonNull;

public interface EmailService {

  void sendEmail(@NonNull IEmail email);

}
