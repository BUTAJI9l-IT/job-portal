package muni.fi.cz.jobportal.api.common;

import java.util.UUID;
import lombok.Data;
import muni.fi.cz.jobportal.enums.JobPortalScope;

@Data
public class UserDto {

  private UUID id;
  private UUID nui;
  private String email;
  private String name;
  private String lastName;
  private JobPortalScope scope;
}
