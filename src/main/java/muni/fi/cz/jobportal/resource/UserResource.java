package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.USER;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = USER)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

}
