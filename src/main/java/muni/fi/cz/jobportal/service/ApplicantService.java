package muni.fi.cz.jobportal.service;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import muni.fi.cz.jobportal.api.common.ApplicantDto;
import muni.fi.cz.jobportal.api.common.ExperienceDto;
import muni.fi.cz.jobportal.api.detail.ApplicantDetailDto;
import muni.fi.cz.jobportal.api.request.ApplicantCreateDto;
import muni.fi.cz.jobportal.api.request.ApplicantUpdateDto;
import muni.fi.cz.jobportal.api.search.ApplicantQueryParams;
import org.springframework.lang.NonNull;

public interface ApplicantService extends
  CRUDService<ApplicantCreateDto, ApplicantDto, ApplicantUpdateDto, ApplicantDetailDto, ApplicantQueryParams> {

  @NonNull
  ByteArrayInputStream generateCV();

  @NonNull
  ApplicantDetailDto addExperience(@NonNull UUID applicantId, @NonNull ExperienceDto payload);

  @NonNull
  ApplicantDetailDto removeExperience(@NonNull UUID applicantId, @NonNull UUID experienceId);
}
