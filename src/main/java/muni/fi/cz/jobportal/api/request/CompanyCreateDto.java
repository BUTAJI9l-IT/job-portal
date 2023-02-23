package muni.fi.cz.jobportal.api.request;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateDto {

  @NotNull
  private UUID user;
  @NotBlank
  private String companyName;
  private String companyLink;
  private CompanyNumberOfEmployees companySize;
  private String description;
}
