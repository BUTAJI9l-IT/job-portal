package muni.fi.cz.jobportal.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
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
