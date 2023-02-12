package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.JobPositionDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.detail.JobPositionDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicationCreateDto;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = JOB_POSITION)
@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
@SecurityRequirement(name = BEARER_AUTH)
public class JobPositionResource {

  @PostMapping("/{jobPositionId}")
  @Operation(summary = "Apply for a job position")
  public ResponseEntity<JobPositionDetailDto> apply(@PathVariable("jobPositionId") UUID jobPositionId,
    @Valid @RequestBody ApplicationCreateDto payload) {
    return ResponseEntity.ok().build();
  }

  @PostMapping
  @Operation(summary = "Create a job position")
  public ResponseEntity<JobPositionDetailDto> createJobPosition(@Valid @RequestBody ApplicationCreateDto payload) {
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{jobPositionId}/state")
  @Operation(summary = "Change a state of a job position")
  public ResponseEntity<JobPositionDetailDto> changeJobPositionState(
    @PathVariable("jobPositionId") UUID applicationId) {
    return ResponseEntity.ok().build();
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all job positions")
  public Page<JobPositionDto> getJobPositions(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q) {
    return Page.empty();
  }

  @GetMapping("/{jobPositionId}")
  @Operation(summary = "Returns a job position by given id")
  public ResponseEntity<ApplicantDetailDto> getJobPosition(@PathVariable("jobPositionId") UUID jobPositionId) {
    return ResponseEntity.ok().build();
  }
}
