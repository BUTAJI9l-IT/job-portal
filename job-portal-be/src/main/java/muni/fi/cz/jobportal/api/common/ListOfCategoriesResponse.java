package muni.fi.cz.jobportal.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfCategoriesResponse {

  private List<ReferenceDto> jobCategories;
}
