package muni.fi.cz.jobportal.service;

import java.util.UUID;
import muni.fi.cz.jobportal.api.common.CategoryDto;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.search.OccupationQueryParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface JobPositionCategoryService {

  @NonNull
  ReferenceDto create(@NonNull String name);

  @NonNull
  ListOfCategoriesResponse findAll();

  @NonNull
  Page<ReferenceDto> searchOccupations(@NonNull Pageable pageable, @NonNull OccupationQueryParams params);

  void delete(@NonNull UUID id);

  @NonNull
  CategoryDto getOccupationsByCategory(@NonNull UUID id);
}
