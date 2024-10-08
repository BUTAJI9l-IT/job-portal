package com.github.butaji9l.jobportal.be.resource;

import com.github.butaji9l.jobportal.be.annotation.JobPortalSecuredController;
import com.github.butaji9l.jobportal.be.api.ApiTags;
import com.github.butaji9l.jobportal.be.api.common.ApplicationDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicationDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicationUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicationQueryParams;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import com.github.butaji9l.jobportal.be.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller with application entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = ApiTags.APPLICATION)
@RequestMapping("/applications")
@JobPortalSecuredController
@RequiredArgsConstructor
public class ApplicationResource {

  private final ApplicationService applicationService;

  @GetMapping("/{applicationId}")
  @Operation(summary = "${api.application.getOne.summary}", description = "${api.application.getOne.description}")
  public ResponseEntity<ApplicationDetailDto> getApplication(
    @PathVariable("applicationId") UUID applicationId) {
    return ResponseEntity.ok(applicationService.findOne(applicationId));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "${api.application.getAll.summary}", description = "${api.application.getAll.description}")
  public Page<ApplicationDto> getApplications(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q,
    @RequestParam(required = false) UUID applicant,
    @RequestParam(required = false) UUID jobPosition,
    @RequestParam(required = false) UUID company,
    @RequestParam(required = false) ApplicationState status,
    @RequestParam(required = false) Instant dateFrom,
    @RequestParam(required = false) Instant dateTo
  ) {
    return applicationService.findAll(pageable, ApplicationQueryParams.builder()
      .qList(q)
      .applicant(applicant)
      .jobPosition(jobPosition)
      .company(company)
      .status(status)
      .dateFrom(dateFrom)
      .dateTo(dateTo)
      .build());
  }

  @PutMapping("/{applicationId}/state")
  @Operation(summary = "${api.application.update.summary}", description = "${api.application.update.description}")
  public ResponseEntity<ApplicationDetailDto> changeApplicationState(
    @PathVariable("applicationId") UUID applicationId,
    @Valid @RequestBody ApplicationUpdateDto payload) {
    return ResponseEntity.ok(applicationService.update(applicationId, payload));
  }

  @DeleteMapping("/{applicationId}")
  @Operation(summary = "${api.application.delete.summary}", description = "${api.application.delete.description}")
  public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") UUID applicationId) {
    applicationService.delete(applicationId);
    return ResponseEntity.noContent().build();
  }

}
