package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.APPLICANT;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = APPLICANT)
@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
@SecurityRequirement(name = BEARER_AUTH)
public class ApplicantResource {

  @GetMapping("/{applicantId}")
  @Operation(summary = "Returns an applicant by given id")
  public ResponseEntity<ApplicantDetailDto> getApplicant(@PathVariable("applicantId") UUID applicantId) {
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{applicantId}")
  @Operation(summary = "Updates an applicant by given id")
  public ResponseEntity<ApplicantDetailDto> updateApplicant(@PathVariable("applicantId") UUID applicantId,
    @Valid @RequestBody ApplicantUpdateDto payload) {
    return ResponseEntity.ok().build();
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all applicants")
  public Page<ApplicantDto> getApplicants(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q,
    @RequestParam(required = false) UUID jobPositionId) {
    return Page.empty();
  }

}
