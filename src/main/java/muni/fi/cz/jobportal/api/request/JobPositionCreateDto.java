package muni.fi.cz.jobportal.api.request;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobPositionCreateDto {

  @NotNull
  private UUID company;
  private String positionName;
  private String country;
  private String state;
  private String city;
  @Email
  private String contactEmail;
  private String detail;
  private List<@NotNull UUID> jobCategories;
}
