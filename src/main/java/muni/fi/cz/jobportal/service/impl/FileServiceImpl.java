package muni.fi.cz.jobportal.service.impl;

import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.api.common.AvatarBase64Dto;
import muni.fi.cz.jobportal.domain.File;
import muni.fi.cz.jobportal.exception.UploadFailedException;
import muni.fi.cz.jobportal.repository.FileRepository;
import muni.fi.cz.jobportal.repository.UserRepository;
import muni.fi.cz.jobportal.service.FileService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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
  public static final String DEFAULT_AVATAR = "classpath:img/default_avatar.png";

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public AvatarBase64Dto uploadAvatar(@NonNull UUID userId, @NonNull MultipartFile file) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    if (user.getAvatar() != null) {
      fileRepository.delete(user.getAvatar());
    }
    final File fileDB;
    try {
      fileDB = new File(null, StringUtils.cleanPath(file.getOriginalFilename()), file.getContentType(),
          file.getBytes(), user);
    } catch (IOException ignored) {
      throw new UploadFailedException();
    }

    user.setAvatar(fileRepository.saveAndFlush(fileDB));
    userRepository.saveAndFlush(user);
    return new AvatarBase64Dto(Base64Utils.encodeToString(user.getAvatar().getData()));
  }

  @NonNull
  @Override
  @PreAuthorize("@authorityValidator.isAdmin() || @authorityValidator.isCurrentUser(#userId)")
  public AvatarBase64Dto deleteAvatar(@NonNull UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    final var avatar = user.getAvatar();
    if (avatar != null) {
      fileRepository.delete(avatar);
    }
    return new AvatarBase64Dto(getDefaultAvatar());
  }

  @NonNull
  @Override
  @Transactional(readOnly = true)
  public AvatarBase64Dto getAvatar(@NonNull UUID userId) {
    final var user = userRepository.getOneByIdOrThrowNotFound(userId);
    return user.getAvatar() == null ? new AvatarBase64Dto(getDefaultAvatar())
        : new AvatarBase64Dto(Base64Utils.encodeToString(user.getAvatar().getData()));
  }

  private String getDefaultAvatar() {
    try {
      return Base64Utils.encodeToString(resourceLoader.getResource(DEFAULT_AVATAR).getInputStream().readAllBytes());
    } catch (IOException ignored) {
      return null;
    }
  }
}
