package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.CategoryDto;
import com.github.butaji9l.jobportal.be.api.common.ListOfCategoriesResponse;
import com.github.butaji9l.jobportal.be.api.common.ReferenceDto;
import com.github.butaji9l.jobportal.be.api.search.OccupationQueryParams;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface JobPositionCategoryService {

  /**
   * Method creates a job category.
   *
   * @param name name for a new category
   * @return name and id of created category
   */
  @NonNull
  ReferenceDto create(@NonNull String name);

  /**
   * Lists available job categories.
   *
   * @return name and id of created category
   */
  @NonNull
  ListOfCategoriesResponse findAll();

  /**
   * Search for occupations.
   *
   * @param pageable pageable parameter
   * @param params   search parameters
   * @return pageable model of the all occupations matched input parameters
   */
  @NonNull
  Page<ReferenceDto> searchOccupations(@NonNull Pageable pageable,
    @NonNull OccupationQueryParams params);

  /**
   * Deletes an entity by given id in datasource if exists.
   *
   * @param id id of the entity to be deleted
   */
  void delete(@NonNull UUID id);

  /**
   * Lists available occupations for given job category.
   *
   * @param id job category id
   * @return category dto with available occupations
   */
  @NonNull
  CategoryDto getOccupationsByCategory(@NonNull UUID id);
}
