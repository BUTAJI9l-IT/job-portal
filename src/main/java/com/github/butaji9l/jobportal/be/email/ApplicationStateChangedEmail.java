package com.github.butaji9l.jobportal.be.email;

import static com.github.butaji9l.jobportal.be.enums.TemplateParameter.COMPANY;
import static com.github.butaji9l.jobportal.be.enums.TemplateParameter.JOB_POSITION;
import static com.github.butaji9l.jobportal.be.enums.TemplateParameter.STATUS;

import com.github.butaji9l.jobportal.be.api.email.ApplicationEmailDto;
import com.github.butaji9l.jobportal.be.enums.TemplateParameter;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;

public class ApplicationStateChangedEmail extends AbstractEmail {

  @Getter
  private final Map<TemplateParameter, Object> contextVariables = new EnumMap<>(
    TemplateParameter.class);
  private static final String TEMPLATE_NAME = "email_state_application.html";

  public ApplicationStateChangedEmail(ApplicationEmailDto details) {
    super(TEMPLATE_NAME, details);
    contextVariables.put(STATUS, details.getState().toString());
    contextVariables.put(JOB_POSITION, details.getJobPosition());
    contextVariables.put(COMPANY, details.getCompany());
  }
}
