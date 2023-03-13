package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.APPLICANT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import muni.fi.cz.jobportal.service.ApplicantService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller with applicant entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = APPLICANT)
@RequestMapping("/applicants")
@JobPortalSecuredController
@RequiredArgsConstructor
public class ApplicantResource {

  private final ApplicantService applicantService;

  @GetMapping("/{applicantId}")
  @Operation(summary = "Returns an applicant by given id")
  public ResponseEntity<ApplicantDetailDto> getApplicant(@PathVariable("applicantId") UUID applicantId) {
    return ResponseEntity.ok(applicantService.findOne(applicantId));
  }

  @PutMapping("/{applicantId}")
  @Operation(summary = "Updates an applicant by given id")
  public ResponseEntity<ApplicantDetailDto> updateApplicant(@PathVariable("applicantId") UUID applicantId,
    @Valid @RequestBody ApplicantUpdateDto payload) {
    return ResponseEntity.ok(applicantService.update(applicantId, payload));
  }

  @PostMapping("/{applicantId}/experience")
  @Operation(summary = "Add experience")
  public ResponseEntity<ApplicantDetailDto> addExperience(@PathVariable("applicantId") UUID applicantId,
    @Valid @RequestBody ExperienceDto payload) {
    return ResponseEntity.ok(applicantService.addExperience(applicantId, payload));
  }

  @DeleteMapping("/{applicantId}/experience/{experienceId}")
  @Operation(summary = "Delete experience")
  public ResponseEntity<ApplicantDetailDto> removeExperience(@PathVariable("applicantId") UUID applicantId,
    @PathVariable("experienceId") UUID experienceId) {
    return ResponseEntity.ok(applicantService.removeExperience(applicantId, experienceId));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all applicants")
  public Page<ApplicantDto> getApplicants(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q,
    @RequestParam(required = false) UUID jobPosition) {
    return applicantService.findAll(pageable, ApplicantQueryParams.builder()
      .qList(q)
      .jobPosition(jobPosition)
      .build()
    );
  }

  @GetMapping(value = "/{applicantId}/generate-cv", produces = MediaType.APPLICATION_PDF_VALUE)
  @Operation(summary = "Generates CV for given applicant")
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
  public ResponseEntity<Resource> generateCV(@PathVariable("applicantId") UUID applicantId) {
    final var resource = new InputStreamResource(applicantService.generateCV(applicantId));
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

}
