package muni.fi.cz.jobportal.resource;

import static muni.fi.cz.jobportal.api.ApiTags.USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalSecuredController;
import muni.fi.cz.jobportal.api.common.AvatarBase64Dto;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.service.FileService;
import muni.fi.cz.jobportal.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller with user entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = USER)
@JobPortalSecuredController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;
  private final FileService fileService;

  @PutMapping("/{userId}")
  @Operation(summary = "Updates user's password")
  public ResponseEntity<UserDto> updatePassword(@PathVariable("userId") UUID userId,
      @RequestBody @Valid UserUpdateDto payload) {
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

  @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload new user avatar")
  public ResponseEntity<AvatarBase64Dto> uploadAvatar(@PathVariable UUID userId,
      @RequestPart("file") MultipartFile file) {
    return ResponseEntity.ok(fileService.uploadAvatar(userId, file));
  }

  @DeleteMapping("/{userId}/avatar")
  @Operation(summary = "Delete old user avatar")
  public ResponseEntity<AvatarBase64Dto> deleteAvatar(@PathVariable UUID userId) {
    return ResponseEntity.ok(fileService.deleteAvatar(userId));
  }

  @GetMapping("/{userId}/avatar")
  @Operation(summary = "Get user avatar")
  public ResponseEntity<AvatarBase64Dto> getAvatar(@PathVariable UUID userId) {
    return ResponseEntity.ok(fileService.getAvatar(userId));
  }
}
