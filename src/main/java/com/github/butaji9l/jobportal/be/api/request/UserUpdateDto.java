package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.annotation.RepeatPassword;
import com.github.butaji9l.jobportal.be.api.common.RepeatPasswordDto;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class UserUpdateDto {

  @Valid
  @RepeatPassword
  private RepeatPasswordDto password;
  private String oldPassword;

}
