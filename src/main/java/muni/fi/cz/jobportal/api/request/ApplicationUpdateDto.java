package muni.fi.cz.jobportal.api.request;

import lombok.Data;
import muni.fi.cz.jobportal.enums.ApplicationState;

@Data
public class ApplicationUpdateDto {

  private ApplicationState state;
}
