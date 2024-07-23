package com.github.butaji9l.jobportal.be.resource;

import static com.github.butaji9l.jobportal.be.configuration.constants.ApplicationConstants.BEARER_AUTH;

import com.github.butaji9l.jobportal.be.annotation.JobPortalController;
import com.github.butaji9l.jobportal.be.api.ApiTags;
import com.github.butaji9l.jobportal.be.api.common.FavouritesJobsResponse;
import com.github.butaji9l.jobportal.be.api.common.JobPositionDto;
import com.github.butaji9l.jobportal.be.api.detail.JobPositionDetailDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionCreateDto;
import com.github.butaji9l.jobportal.be.api.request.JobPositionUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.JobPositionQueryParams;
import com.github.butaji9l.jobportal.be.enums.PositionState;
import com.github.butaji9l.jobportal.be.service.JobPositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

/**
 * Controller with job position entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = ApiTags.JOB_POSITION)
@RequestMapping("/positions")
@JobPortalController
@RequiredArgsConstructor
public class JobPositionResource {

  private final JobPositionService jobPositionService;

  @PostMapping("/applicants/{applicantId}/apply/{jobPositionId}")
  @Operation(summary = "${api.job.apply.summary}", description = "${api.job.apply.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> apply(@PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.apply(applicantId, jobPositionId));
  }

  @PostMapping
  @Operation(summary = "${api.job.create.summary}", description = "${api.job.create.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> createJobPosition(
    @Valid @RequestBody JobPositionCreateDto payload) {
    return ResponseEntity.ok(jobPositionService.create(payload));
  }

  @PutMapping("/{jobPositionId}")
  @Operation(summary = "${api.job.update.summary}", description = "${api.job.update.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<JobPositionDetailDto> updateJobPosition(
    @PathVariable("jobPositionId") UUID jobPositionId,
    @Valid @RequestBody JobPositionUpdateDto payload) {
    return ResponseEntity.ok(jobPositionService.update(jobPositionId, payload));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "${api.job.getAll.summary}", description = "${api.job.getAll.description}")
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
  @Operation(summary = "${api.job.getOne.summary}", description = "${api.job.getOne.description}")
  public ResponseEntity<JobPositionDetailDto> getJobPosition(
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.findOne(jobPositionId));
  }

  @GetMapping("/applicants/{applicantId}/favorites")
  @Operation(summary = "${api.job.favorites.summary}", description = "${api.job.favorites.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> getFavorites(
    @PathVariable("applicantId") UUID applicantId) {
    return ResponseEntity.ok(jobPositionService.getFavorites(applicantId));
  }

  @PutMapping("/applicants/{applicantId}/favourites/{jobPositionId}/add")
  @Operation(summary = "${api.job.addFavorites.summary}", description = "${api.job.addFavorites.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> addToFavorites(
    @PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.addToFavorites(applicantId, jobPositionId));
  }

  @PutMapping("/applicants/{applicantId}/favourites/{jobPositionId}/remove")
  @Operation(summary = "${api.job.deleteFavorites.summary}", description = "${api.job.deleteFavorites.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<FavouritesJobsResponse> removeFromFavorites(
    @PathVariable("applicantId") UUID applicantId,
    @PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok(jobPositionService.removeFromFavorites(applicantId, jobPositionId));
  }
}
