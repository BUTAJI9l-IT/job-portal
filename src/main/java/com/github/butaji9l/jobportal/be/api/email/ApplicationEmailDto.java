package com.github.butaji9l.jobportal.be.api.email;

import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ApplicationEmailDto extends EmailDetailsDto {

  private ApplicationState state;
  private String jobPosition;
  private String company;
}
