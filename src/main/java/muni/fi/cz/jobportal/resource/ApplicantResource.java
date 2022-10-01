package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.APPLICANT;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = APPLICANT)
@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantResource {

}
