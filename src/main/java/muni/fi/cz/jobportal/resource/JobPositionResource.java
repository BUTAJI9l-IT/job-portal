package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.JOB_POSITION;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = JOB_POSITION)
@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class JobPositionResource {

}
