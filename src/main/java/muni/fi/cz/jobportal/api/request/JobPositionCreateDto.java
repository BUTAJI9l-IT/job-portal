package muni.fi.cz.jobportal.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Data;

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
