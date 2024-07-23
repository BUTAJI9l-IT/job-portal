package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.AvatarResponse;
import java.util.UUID;
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
  AvatarResponse uploadAvatar(@NonNull UUID userId, @NonNull MultipartFile file);

  /**
   * Deletes user's avatar file
   *
   * @param userId user id
   * @return updated user's avatar (default) base64 representation
   */
  @NonNull
  AvatarResponse deleteAvatar(@NonNull UUID userId);

  /**
   * Returns user's avatar base64 representation or default avatar if user does not have one.
   *
   * @param userId user id
   * @return user's avatar base64 representation
   */
  @NonNull
  AvatarResponse getAvatar(@NonNull UUID userId);
}
