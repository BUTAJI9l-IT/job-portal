package muni.fi.cz.jobportal.email;

import java.util.Map;
import muni.fi.cz.jobportal.enums.TemplateParameter;

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
