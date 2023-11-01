package muni.fi.cz.jobportal.api.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class JobPositionCreateDto {

  @NotNull
  private UUID company;
  @NotNull
  @NotBlank
  private String positionName;
  private String country;
  private String state;
  private String city;
  @Email
  @NotNull
  private String contactEmail;
  private String detail;
  private List<@NotNull UUID> jobCategories;
}
