package com.github.butaji9l.jobportal.be.email;

import com.github.butaji9l.jobportal.be.api.email.EmailDetailsDto;
import com.github.butaji9l.jobportal.be.enums.TemplateParameter;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;

/**
 * Abstract {@link IEmail} interface implementation.
 *
 * @author Vitalii Bortsov
 */
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
