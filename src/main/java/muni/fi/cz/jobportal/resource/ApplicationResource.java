package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.APPLICATION;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.ApplicationDto;
import muni.fi.cz.jobportal.api.request.ApplicationUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicationQueryParams;
import muni.fi.cz.jobportal.service.ApplicationService;
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

@Tag(name = APPLICATION)
@RequestMapping("/applications")
@JobPortalSecuredController
@RequiredArgsConstructor
public class ApplicationResource {

  private final ApplicationService applicationService;

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all applicants")
  public Page<ApplicationDto> getApplications(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q,
    @RequestParam(required = false) UUID jobPositionId) {
    return applicationService.findAll(pageable, ApplicationQueryParams.builder().build());
  }

  @PutMapping("/{applicationId}/state")
  @Operation(summary = "Change a state of an application", description = "Company can change a state an application")
  public ResponseEntity<ApplicationDto> changeApplicationState(@PathVariable("applicationId") UUID applicationId,
    @Valid @RequestBody ApplicationUpdateDto payload) {
    return ResponseEntity.ok(applicationService.update(applicationId, payload));
  }

  @DeleteMapping("/{applicationId}")
  @Operation(summary = "Delete an application")
  public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") UUID applicationId) {
    applicationService.delete(applicationId);
    return ResponseEntity.noContent().build();
  }

}
