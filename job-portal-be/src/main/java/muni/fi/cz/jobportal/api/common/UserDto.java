package muni.fi.cz.jobportal.api.common;

import lombok.Data;
import muni.fi.cz.jobportal.enums.JobPortalScope;

import java.util.UUID;

@Data
public class UserDto {

  private UUID id;
  private UUID nui;
  private String email;
  private String name;
  private String lastName;
  private JobPortalScope scope;
}
