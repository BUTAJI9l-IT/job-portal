package com.github.butaji9l.jobportal.be.api.request;

import com.github.butaji9l.jobportal.be.enums.PositionState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class JobPositionUpdateDto {

  @NotNull
  private PositionState status;
  private String positionName;
  private String country;
  private String state;
  private String city;
  @Email
  private String contactEmail;
  private String detail;
  private List<@NotNull UUID> jobCategories;
}
