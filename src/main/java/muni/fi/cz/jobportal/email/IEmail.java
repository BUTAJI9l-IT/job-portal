package muni.fi.cz.jobportal.email;

import java.util.Map;
import muni.fi.cz.jobportal.enums.TemplateParameter;

public interface IEmail {


  String getTemplateName();

  String getRecipient();

  Map<TemplateParameter, Object> getContextVariables();
}
