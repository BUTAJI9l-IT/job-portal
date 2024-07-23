package com.github.butaji9l.jobportal.be.email;

import com.github.butaji9l.jobportal.be.enums.TemplateParameter;
import java.util.Map;

public interface IEmail {

  /**
   * Getter for template name of the email.
   */
  String getTemplateName();

  /**
   * Getter for recipient of the email.
   */
  String getRecipient();

  /**
   * Getter for context variables for the email template.
   */
  Map<TemplateParameter, Object> getContextVariables();
}
