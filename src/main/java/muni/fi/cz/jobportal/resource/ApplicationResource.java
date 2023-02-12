package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.APPLICATION;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.service.ApplicationService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = APPLICATION)
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@SecurityRequirement(name = BEARER_AUTH)
public class ApplicationResource {

  private final ApplicationService applicationService;

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all applicants")
  public Page<ApplicantDto> getApplications(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q,
    @RequestParam(required = false) UUID jobPositionId) {
    return Page.empty();
  }

  @GetMapping(value = "/generate-cv", produces = MediaType.APPLICATION_PDF_VALUE)
  @Operation(summary = "Generates CV for authorized user")
  @ApiResponse(
    responseCode = "200",
    content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE, schema = @Schema(type = "string", format = "binary")),
    headers = {
      @Header(
        name = HttpHeaders.CONTENT_DISPOSITION,
        description = "containing file name",
        schema = @Schema(
          type = "string"
        )
      )
    })
  public ResponseEntity<Resource> generateCV() {
    final var resource = new InputStreamResource(applicationService.generateCV());
    final var cd = ContentDisposition.builder("attachment")
      .name("Job_Portal_CV.pdf")
      .filename("Job_Portal_CV.pdf")
      .build()
      .toString();
    return ResponseEntity
      .ok()
      .cacheControl(CacheControl.noCache().mustRevalidate())
      .header(HttpHeaders.CONTENT_DISPOSITION, cd)
      .contentType(MediaType.APPLICATION_PDF)
      .body(resource);
  }

  @PutMapping("/{applicationId}/state")
  @Operation(summary = "Change a state of an application", description = "Company can change a state an application")
  public ResponseEntity<ApplicantDetailDto> changeApplicationState(@PathVariable("applicationId") UUID applicationId) {
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{applicationId}")
  @Operation(summary = "Delete an application")
  public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") UUID applicationId) {
    return ResponseEntity.noContent().build();
  }


}
