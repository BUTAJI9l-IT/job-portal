package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.COMPANY;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalController;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.search.CompanyQueryParams;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;
import muni.fi.cz.jobportal.service.CompanyService;
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

/**
 * Controller with company entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = COMPANY)
@RequestMapping("/companies")
@JobPortalController
@RequiredArgsConstructor
public class CompanyResource {

  private final CompanyService companyService;

  @GetMapping("/{companyId}")
  @Operation(summary = "${api.company.getOne.summary}", description = "${api.company.getOne.description}")
  public ResponseEntity<CompanyDetailDto> getCompany(@PathVariable("companyId") UUID companyId) {
    return ResponseEntity.ok(companyService.findOne(companyId));
  }

  @PutMapping("/{companyId}")
  @Operation(summary = "${api.company.update.summary}", description = "${api.company.update.description}")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<CompanyDetailDto> updateCompany(@PathVariable("companyId") UUID companyId,
    @Valid @RequestBody CompanyUpdateDto payload) {
    return ResponseEntity.ok(companyService.update(companyId, payload));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "${api.company.getAll.summary}", description = "${api.company.getAll.description}")
  public Page<CompanyDto> getCompanies(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q,
    @RequestParam(required = false) CompanyNumberOfEmployees companySize) {
    return companyService.findAll(pageable,
      CompanyQueryParams.builder().qList(q).companySize(companySize).build());
  }
}
