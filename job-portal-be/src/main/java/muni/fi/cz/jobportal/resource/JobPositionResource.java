package muni.fi.cz.jobportal.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalController;
import muni.fi.cz.jobportal.api.common.FavouritesJobsResponse;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.JobPositionCreateDto;
import muni.fi.cz.jobportal.api.request.JobPositionUpdateDto;
import muni.fi.cz.jobportal.api.search.JobPositionQueryParams;
import muni.fi.cz.jobportal.enums.PositionState;
import muni.fi.cz.jobportal.service.JobPositionService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

/**
 * Controller with job position entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = JOB_POSITION)
@RequestMapping("/positions")
@JobPortalController
@RequiredArgsConstructor
public class JobPositionResource {

  private final JobPositionService jobPositionService;

  @PostMapping("/applicants/{applicantId}/apply/{jobPositionId}")
  @Operation(summary = "Apply for a job position")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> apply(@PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.apply(applicantId, jobPositionId));
  }

  @PostMapping
  @Operation(summary = "Create a job position")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> createJobPosition(@Valid @RequestBody JobPositionCreateDto payload) {
    return ResponseEntity.ok(jobPositionService.create(payload));
  }

  @PutMapping("/{jobPositionId}")
  @Operation(summary = "Change a state of a job position")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> updateJobPosition(@PathVariable("jobPositionId") UUID jobPositionId,
    @Valid @RequestBody JobPositionUpdateDto payload) {
    return ResponseEntity.ok(jobPositionService.update(jobPositionId, payload));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all job positions")
  public Page<JobPositionDto> getJobPositions(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q,
    @RequestParam(required = false) PositionState status,
    @RequestParam(required = false) List<UUID> categories,
    @RequestParam(required = false) List<UUID> companies
  ) {
    return jobPositionService.findAll(pageable, JobPositionQueryParams.builder()
      .qList(q)
      .category(categories)
      .company(companies)
      .status(status)
      .build());
  }

  @GetMapping("/{jobPositionId}")
  @Operation(summary = "Returns a job position by given id")
  public ResponseEntity<JobPositionDetailDto> getJobPosition(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.findOne(jobPositionId));
  }

  @GetMapping("/applicants/{applicantId}/favorites")
  @Operation(summary = "Get favorite jobs of a current user")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> getFavorites(@PathVariable("applicantId") UUID applicantId) {
    return ResponseEntity.ok(jobPositionService.getFavorites(applicantId));
  }

  @PutMapping("/applicants/{applicantId}/favourites/{jobPositionId}/add")
  @Operation(summary = "Add to favorite jobs of a current user")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> addToFavorites(@PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.addToFavorites(applicantId, jobPositionId));
  }

  @PutMapping("/applicants/{applicantId}/favourites/{jobPositionId}/remove")
  @Operation(summary = "Remove from favorite jobs of a current user")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> removeFromFavorites(@PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.removeFromFavorites(applicantId, jobPositionId));
  }
}
