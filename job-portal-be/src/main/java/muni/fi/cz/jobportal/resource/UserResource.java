package muni.fi.cz.jobportal.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalController;
import muni.fi.cz.jobportal.api.common.AvatarResponse;
import muni.fi.cz.jobportal.api.common.PreferencesDto;
import muni.fi.cz.jobportal.api.common.UserDto;
import muni.fi.cz.jobportal.api.request.UserUpdateDto;
import muni.fi.cz.jobportal.api.search.UserQueryParams;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import muni.fi.cz.jobportal.service.FileService;
import muni.fi.cz.jobportal.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.api.ApiTags.USER;
import static muni.fi.cz.jobportal.configuration.constants.ApplicationConstants.BEARER_AUTH;

/**
 * Controller with user entity related endpoints.
 *
 * @author Vitalii Bortsov
 */
@Tag(name = USER)
@JobPortalController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

  private final UserService userService;
  private final FileService fileService;

  @PutMapping("/{userId}")
  @Operation(summary = "Updates user's password")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<UserDto> updatePassword(@PathVariable("userId") UUID userId,
    @RequestBody @Valid UserUpdateDto payload) {
    return ResponseEntity.ok(userService.update(userId, payload));
  }

  @GetMapping("/{userId}")
  @Operation(summary = "Get user info")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<UserDto> getUserInfo(@PathVariable("userId") UUID userId) {
    return ResponseEntity.ok(userService.findOne(userId));
  }

  @GetMapping("/{userId}/preferences")
  @Operation(summary = "Get user preferences")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<PreferencesDto> getUserPreferences(@PathVariable("userId") UUID userId) {
    return ResponseEntity.ok(userService.getUserPreferences(userId));
  }

  @PutMapping("/{userId}/preferences")
  @Operation(summary = "Update user preferences")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<PreferencesDto> updatePreferences(@PathVariable("userId") UUID userId,
    @RequestBody @Valid PreferencesDto payload) {
    return ResponseEntity.ok(userService.updatePreferences(userId, payload));
  }

  @DeleteMapping("/{userId}")
  @Operation(summary = "Delete a user")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @PageableAsQueryParam
  @Operation(summary = "Returns all users")
  @SecurityRequirement(name = BEARER_AUTH)
  public Page<UserDto> getUsers(@Parameter(hidden = true) Pageable pageable,
    @RequestParam(required = false) List<String> q,
    @RequestParam(required = false) JobPortalScope scope
  ) {
    return userService.findAll(pageable, UserQueryParams.builder()
      .qList(q)
      .scope(scope)
      .build()
    );
  }

  @PostMapping(value = "/{userId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload new user avatar")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<Resource> uploadAvatar(@PathVariable UUID userId,
    @RequestPart("file") MultipartFile file) {
    return getResourceResponseEntity(userId, fileService.uploadAvatar(userId, file));
  }

  @DeleteMapping("/{userId}/avatar")
  @Operation(summary = "Delete old user avatar")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<Resource> deleteAvatar(@PathVariable UUID userId) {
    return getResourceResponseEntity(userId, fileService.deleteAvatar(userId));
  }

  @GetMapping("/{userId}/avatar")
  @Operation(summary = "Get user avatar")
  public ResponseEntity<Resource> getAvatar(@PathVariable UUID userId) {
    return getResourceResponseEntity(userId, fileService.getAvatar(userId));
  }

  @GetMapping("/{userId}/avatar-secure")
  @Operation(summary = "Get user avatar authenticated")
  @SecurityRequirement(name = BEARER_AUTH)
  public ResponseEntity<Resource> getAvatarSecured(@PathVariable UUID userId) {
    return getResourceResponseEntity(userId, fileService.getAvatar(userId));
  }

  private static ResponseEntity<Resource> getResourceResponseEntity(UUID userId, AvatarResponse avatar) {
    final var cd = ContentDisposition.builder("inline")
      .name("avatar_" + userId)
      .filename("avatar_" + userId)
      .build()
      .toString();
    return ResponseEntity.ok()
      .contentType(avatar.mediaType())
      .cacheControl(CacheControl.noCache().mustRevalidate())
      .header(HttpHeaders.CONTENT_DISPOSITION, cd)
      .body(avatar.resource());
  }
}
