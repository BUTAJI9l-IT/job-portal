package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = USER)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;

  @PutMapping("/{userId}")
  @Operation(summary = "Updates user's password")
  public ResponseEntity<UserDto> updatePassword(@PathVariable("userId") UUID userId,
    @Valid @RequestBody UserUpdateDto payload) {
    return ResponseEntity.ok(userService.update(userId, payload));
  }

  @GetMapping("/{userId}")
  @Operation(summary = "Get user info")
  public ResponseEntity<UserDto> getUserInfo(@PathVariable("userId") UUID userId) {
    return ResponseEntity.ok(userService.findOne(userId));
  }

  @DeleteMapping("/{userId}")
  @Operation(summary = "Delete a user")
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all users")
  public Page<UserDto> getUsers(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) String q,
    @RequestParam(required = false) JobPortalScope scope
  ) {
    return userService.findAll(pageable, UserQueryParams.builder()
      .q(q)
      .scope(scope)
      .build()
    );
  }
}
