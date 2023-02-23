package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.FavouritesJobsResponse;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
import muni.fi.cz.jobportal.service.JobPositionService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = JOB_POSITION)
@RequestMapping("/positions")
@JobPortalSecuredController
@RequiredArgsConstructor
public class JobPositionResource {

  private final JobPositionService jobPositionService;

  @PostMapping("/{jobPositionId}")
  @Operation(summary = "Apply for a job position")
  public ResponseEntity<JobPositionDetailDto> apply(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.apply(jobPositionId));
  }

  @PostMapping
  @Operation(summary = "Create a job position")
  public ResponseEntity<JobPositionDetailDto> createJobPosition(@Valid @RequestBody JobPositionCreateDto payload) {
    return ResponseEntity.ok(jobPositionService.create(payload));
  }

  @PutMapping("/{jobPositionId}")
  @Operation(summary = "Change a state of a job position")
  public ResponseEntity<JobPositionDetailDto> updateJobPosition(@PathVariable("jobPositionId") UUID jobPositionId,
    @Valid @RequestBody JobPositionUpdateDto payload) {
    return ResponseEntity.ok(jobPositionService.update(jobPositionId, payload));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all job positions")
  public Page<JobPositionDto> getJobPositions(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q) {
    return jobPositionService.findAll(pageable, JobPositionQueryParams.builder().build());
  }

  @GetMapping("/{jobPositionId}")
  @Operation(summary = "Returns a job position by given id")
  public ResponseEntity<JobPositionDetailDto> getJobPosition(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.findOne(jobPositionId));
  }

  @GetMapping("/favorites")
  @Operation(summary = "Get favorite jobs of a current user")
  public ResponseEntity<FavouritesJobsResponse> getFavorites() {
    return ResponseEntity.ok(jobPositionService.getFavorites());
  }

  @PutMapping("/favourites/{jobPositionId}/add")
  @Operation(summary = "Add to favorite jobs of a current user")
  public ResponseEntity<FavouritesJobsResponse> addToFavorites(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.addToFavorites(jobPositionId));
  }

  @PutMapping("/favourites/{jobPositionId}/remove")
  @Operation(summary = "Remove from favorite jobs of a current user")
  public ResponseEntity<FavouritesJobsResponse> removeFromFavorites(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.removeFromFavorites(jobPositionId));
  }
}
