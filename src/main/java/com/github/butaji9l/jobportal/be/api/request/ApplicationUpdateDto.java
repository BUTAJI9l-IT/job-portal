package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import lombok.Data;

@Data
public class ApplicationUpdateDto {

  private ApplicationState state;
}
