package muni.fi.cz.jobportal.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalController;
import muni.fi.cz.jobportal.api.common.CategoryDto;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.api.search.OccupationQueryParams;
import muni.fi.cz.jobportal.service.JobPositionCategoryService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION_CATEGORY;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

/**
 * Controller with job category entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = JOB_POSITION_CATEGORY)
@RequestMapping("/job-categories")
@JobPortalController
@RequiredArgsConstructor
public class JobPositionCategoryResource {

  private final JobPositionCategoryService jobPositionCategoryService;

  @PostMapping("/{categoryName}")
  @Operation(summary = "${api.job-cat.create.summary}", description = "${api.job-cat.create.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<ReferenceDto> createCategory(@PathVariable("categoryName") String categoryName) {
    return ResponseEntity.ok(jobPositionCategoryService.create(categoryName));
  }

  @DeleteMapping("/{categoryId}")
  @Operation(summary = "${api.job-cat.delete.summary}", description = "${api.job-cat.delete.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") UUID categoryId) {
    jobPositionCategoryService.delete(categoryId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(summary = "${api.job-cat.getAll.summary}", description = "${api.job-cat.getAll.description}")
  public ResponseEntity<ListOfCategoriesResponse> getCategories() {
    return ResponseEntity.ok(jobPositionCategoryService.findAll());
  }

  @GetMapping("/{categoryId}")
  @Operation(summary = "${api.job-cat.getOne.summary}", description = "${api.job-cat.getOne.description}")
  public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") UUID categoryId) {
    return ResponseEntity.ok(jobPositionCategoryService.getOccupationsByCategory(categoryId));
  }

  @GetMapping("/occupations")
  @PageableAsQueryParam
  @Operation(summary = "${api.job-cat.getOccupations.summary}", description = "${api.job-cat.getOccupations.description}")
  public Page<ReferenceDto> getOccupations(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q) {
    return jobPositionCategoryService.searchOccupations(pageable, OccupationQueryParams.builder().qList(q).build());
  }

}
