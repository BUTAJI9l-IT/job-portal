package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION_CATEGORY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.CategoryDto;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.search.OccupationQueryParams;
import muni.fi.cz.jobportal.service.JobPositionCategoryService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller with job category entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = JOB_POSITION_CATEGORY)
@RequestMapping("/job-categories")
@JobPortalSecuredController
@RequiredArgsConstructor
public class JobPositionCategoryResource {

  private final JobPositionCategoryService jobPositionCategoryService;

  @PostMapping("/{categoryName}")
  @Operation(summary = "Create a category")
  public ResponseEntity<ReferenceDto> createCategory(@PathVariable("categoryName") String categoryName) {
    return ResponseEntity.ok(jobPositionCategoryService.create(categoryName));
  }

  @DeleteMapping("/{categoryId}")
  @Operation(summary = "Delete a category")
  public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") UUID categoryId) {
    jobPositionCategoryService.delete(categoryId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(summary = "List categories")
  public ResponseEntity<ListOfCategoriesResponse> getCategories() {
    return ResponseEntity.ok(jobPositionCategoryService.findAll());
  }

  @GetMapping("/{categoryId}")
  @Operation(summary = "Get occupations")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") UUID categoryId) {
    return ResponseEntity.ok(jobPositionCategoryService.getOccupationsByCategory(categoryId));
  }

  @GetMapping("/occupations")
  @PageableAsQueryParam
  @Operation(summary = "Search for occupation")
  public Page<ReferenceDto> getOccupations(@Parameter(hidden = true) Pageable pageable,
      @RequestParam(required = false) String q) {
    return jobPositionCategoryService.searchOccupations(pageable, OccupationQueryParams.builder().q(q).build());
  }

}
