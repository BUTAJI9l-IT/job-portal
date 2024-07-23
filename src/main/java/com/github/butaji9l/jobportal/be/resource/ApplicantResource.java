package com.github.butaji9l.jobportal.be.resource;

import com.github.butaji9l.jobportal.be.annotation.JobPortalSecuredController;
import com.github.butaji9l.jobportal.be.api.ApiTags;
import com.github.butaji9l.jobportal.be.api.common.ApplicantDto;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicantDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import com.github.butaji9l.jobportal.be.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
@Tag(name = ApiTags.APPLICANT)
@RequestMapping("/applicants")
@JobPortalSecuredController
@RequiredArgsConstructor
public class ApplicantResource {

  private final ApplicantService applicantService;

  @GetMapping("/{applicantId}")
  @Operation(summary = "${api.applicant.getOne.summary}", description = "${api.applicant.getOne.description}")
  public ResponseEntity<ApplicantDetailDto> getApplicant(
    @PathVariable("applicantId") UUID applicantId) {
    return ResponseEntity.ok(applicantService.findOne(applicantId));
  }

  @PutMapping("/{applicantId}")
  @Operation(summary = "${api.applicant.update.summary}", description = "${api.applicant.update.description}")
  public ResponseEntity<ApplicantDetailDto> updateApplicant(
    @PathVariable("applicantId") UUID applicantId,
    @Valid @RequestBody ApplicantUpdateDto payload) {
    return ResponseEntity.ok(applicantService.update(applicantId, payload));
  }

  @PostMapping("/{applicantId}/experience")
  @Operation(summary = "${api.applicant.addExp.summary}", description = "${api.applicant.addExp.description}")
  public ResponseEntity<ApplicantDetailDto> addExperience(
    @PathVariable("applicantId") UUID applicantId,
    @Valid @RequestBody ExperienceDto payload) {
    return ResponseEntity.ok(applicantService.addExperience(applicantId, payload));
  }

  @DeleteMapping("/{applicantId}/experience/{experienceId}")
  @Operation(summary = "${api.applicant.deleteExp.summary}", description = "${api.applicant.deleteExp.description}")
  public ResponseEntity<ApplicantDetailDto> removeExperience(
    @PathVariable("applicantId") UUID applicantId,
    @PathVariable("experienceId") UUID experienceId) {
    return ResponseEntity.ok(applicantService.removeExperience(applicantId, experienceId));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "${api.applicant.getAll.summary}", description = "${api.applicant.getAll.description}")
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
  @Operation(summary = "${api.applicant.cv.summary}", description = "${api.applicant.cv.description}")
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
