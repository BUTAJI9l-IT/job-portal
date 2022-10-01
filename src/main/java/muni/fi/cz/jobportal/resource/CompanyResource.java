package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.COMPANY;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = COMPANY)
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyResource {

}
