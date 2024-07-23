package muni.fi.cz.jobportal.email;

import static muni.fi.cz.jobportal.enums.TemplateParameter.COMPANY;
import static muni.fi.cz.jobportal.enums.TemplateParameter.JOB_POSITION;
import static muni.fi.cz.jobportal.enums.TemplateParameter.STATUS;

import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import muni.fi.cz.jobportal.api.email.ApplicationEmailDto;
import muni.fi.cz.jobportal.enums.TemplateParameter;

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
