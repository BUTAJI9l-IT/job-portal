package muni.fi.cz.jobportal.service;

import java.util.UUID;
import muni.fi.cz.jobportal.api.common.AvatarBase64Dto;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  /**
   * Saves an avatar for given user.
   *
   * @param userId user id
   * @param file   avatar file
   * @return avatar base64 representation
   */
  @NonNull
  AvatarBase64Dto uploadAvatar(@NonNull UUID userId, @NonNull MultipartFile file);

  /**
   * Deletes user's avatar file
   *
   * @param userId user id
   * @return updated user's avatar (default) base64 representation
   */
  @NonNull
  AvatarBase64Dto deleteAvatar(@NonNull UUID userId);

  /**
   * Returns user's avatar base64 representation or default avatar if user does not have one.
   *
   * @param userId user id
   * @return user's avatar base64 representation
   */
  @NonNull
  AvatarBase64Dto getAvatar(@NonNull UUID userId);
}
