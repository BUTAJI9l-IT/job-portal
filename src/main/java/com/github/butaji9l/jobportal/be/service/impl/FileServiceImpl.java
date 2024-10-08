package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.api.common.AvatarResponse;
import com.github.butaji9l.jobportal.be.domain.File;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import com.github.butaji9l.jobportal.be.exception.UploadFailedException;
import com.github.butaji9l.jobportal.be.repository.FileRepository;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import com.github.butaji9l.jobportal.be.service.FileService;
import com.github.butaji9l.jobportal.be.utils.AuthorityValidator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@link FileService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final ResourceLoader resourceLoader;
  private final AuthorityValidator authorityValidator;
  public static final String DEFAULT_AVATAR = "classpath:img/default_avatar.png";

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public AvatarResponse uploadAvatar(@NonNull UUID userId, @NonNull MultipartFile file) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    if (user.getAvatar() != null) {
      fileRepository.delete(user.getAvatar());
    }
    final File fileDB;
    try {
      fileDB = new File(null, StringUtils.cleanPath(file.getOriginalFilename()),
        file.getContentType(),
        file.getBytes(), user);
    } catch (IOException ignored) {
      throw new UploadFailedException();
    }

    user.setAvatar(fileRepository.saveAndFlush(fileDB));
    userRepository.saveAndFlush(user);
    return new AvatarResponse(
      new InputStreamResource(new ByteArrayInputStream(user.getAvatar().getData())),
      MediaType.parseMediaType(user.getAvatar().getExt()));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public AvatarResponse deleteAvatar(@NonNull UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    final var avatar = user.getAvatar();
    if (avatar != null) {
      fileRepository.delete(avatar);
    }
    return getDefaultAvatar();
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public AvatarResponse getAvatar(@NonNull UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    if (user.getScope().equals(JobPortalScope.REGULAR_USER)) {
      final var currentUser = authorityValidator.getCurrentUser();
      if (currentUser == null) {
        throw new AccessDeniedException("Only authorized users can see users' avatars");
      }
      if (!currentUser.equals(userId)) {
        final var auth = userRepository.getOneByIdOrThrowNotFound(currentUser);
        if (auth.getScope().equals(JobPortalScope.REGULAR_USER)) {
          throw new AccessDeniedException("Users can see only their own avatars");
        } else if (auth.getScope().equals(JobPortalScope.COMPANY)) {
          final var authCompany = auth.getCompany().getId();
          if (user.getApplicant().getApplications().stream()
            .noneMatch(application -> application.getJobPosition().getCompany().getId()
              .equals(authCompany))) {
            throw new AccessDeniedException(
              "Companies can see avatars of applicants, which have applied for their jobs");
          }
        }
      }
    }
    return user.getAvatar() == null ? getDefaultAvatar()
      : new AvatarResponse(
        new InputStreamResource(new ByteArrayInputStream(user.getAvatar().getData())),
        MediaType.parseMediaType(user.getAvatar().getExt()));
  }

  private AvatarResponse getDefaultAvatar() {
    return new AvatarResponse(resourceLoader.getResource(DEFAULT_AVATAR), MediaType.IMAGE_PNG);
  }
}
