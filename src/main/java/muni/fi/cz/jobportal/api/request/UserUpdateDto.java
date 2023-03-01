package muni.fi.cz.jobportal.api.request;

import javax.validation.Valid;
import lombok.Data;
import muni.fi.cz.jobportal.annotation.RepeatPassword;
import muni.fi.cz.jobportal.api.common.RepeatPasswordDto;

@Data
public class UserUpdateDto {

  @Valid
  @RepeatPassword
  private RepeatPasswordDto password;
  private String oldPassword;

}
