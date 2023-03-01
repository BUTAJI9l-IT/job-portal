package muni.fi.cz.jobportal.service;

import java.util.UUID;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import org.springframework.lang.NonNull;

public interface JobPositionCategoryService {

  @NonNull
  ReferenceDto create(@NonNull String name);

  @NonNull
  ListOfCategoriesResponse findAll();

  void delete(@NonNull UUID id);
}
