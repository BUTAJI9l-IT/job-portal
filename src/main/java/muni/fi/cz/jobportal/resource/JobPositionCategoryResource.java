package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION_CATEGORY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.ListOfCategoriesResponse;
import muni.fi.cz.jobportal.api.common.ReferenceDto;
import muni.fi.cz.jobportal.service.JobPositionCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
