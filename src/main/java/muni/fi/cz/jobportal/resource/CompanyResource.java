package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.COMPANY;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.CompanyDto;
import muni.fi.cz.jobportal.api.detail.CompanyDetailDto;
import muni.fi.cz.jobportal.api.request.CompanyUpdateDto;
import muni.fi.cz.jobportal.api.search.CompanyQueryParams;
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

@Tag(name = COMPANY)
@RequestMapping("/companies")
@JobPortalSecuredController
@RequiredArgsConstructor
public class CompanyResource {

  private final CompanyService companyService;

  @GetMapping("/{companyId}")
  @Operation(summary = "Returns a company by given id")
  public ResponseEntity<CompanyDetailDto> getCompany(@PathVariable("companyId") UUID companyId) {
    return ResponseEntity.ok(companyService.findOne(companyId));
  }

  @PutMapping("/{companyId}")
  @Operation(summary = "Updates a company by given id")
  public ResponseEntity<CompanyDetailDto> updateCompany(@PathVariable("companyId") UUID companyId,
    @Valid @RequestBody CompanyUpdateDto payload) {
    return ResponseEntity.ok(companyService.update(companyId, payload));
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all companies")
  public Page<CompanyDto> getCompanies(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q) {
    return companyService.findAll(pageable, CompanyQueryParams.builder().build());
  }
}
