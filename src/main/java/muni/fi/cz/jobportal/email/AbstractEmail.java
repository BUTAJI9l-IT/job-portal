package muni.fi.cz.jobportal.email;

import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import muni.fi.cz.jobportal.api.email.EmailDetailsDto;
import muni.fi.cz.jobportal.enums.TemplateParameter;

@Getter
public abstract class AbstractEmail implements IEmail {

  private final String templateName;
  private final String recipient;

  protected AbstractEmail(String templateName, EmailDetailsDto details) {
    this.templateName = templateName;
    this.recipient = details.getRecipient();
  }

  @Override
  public Map<TemplateParameter, Object> getContextVariables() {
    return new EnumMap<>(TemplateParameter.class);
  }
}
