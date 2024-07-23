package com.github.butaji9l.jobportal.be.api.email;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class EmailDetailsDto {

  private String recipient;
}
