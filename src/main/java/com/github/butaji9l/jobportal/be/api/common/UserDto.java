package com.github.butaji9l.jobportal.be.api.common;

import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {

  private UUID id;
  private UUID nui;
  private String email;
  private String name;
  private String lastName;
  private JobPortalScope scope;
}
