package muni.fi.cz.jobportal.service;

import java.util.UUID;
import muni.fi.cz.jobportal.api.common.AvatarBase64Dto;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  @NonNull
  AvatarBase64Dto uploadAvatar(@NonNull UUID userId, @NonNull MultipartFile file);

  @NonNull
  AvatarBase64Dto deleteAvatar(@NonNull UUID userId);

  @NonNull
  AvatarBase64Dto getAvatar(@NonNull UUID userId);
}
