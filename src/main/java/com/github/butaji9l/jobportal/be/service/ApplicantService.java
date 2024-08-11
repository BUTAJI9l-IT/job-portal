package com.github.butaji9l.jobportal.be.service;

import com.github.butaji9l.jobportal.be.api.common.ApplicantDto;
import com.github.butaji9l.jobportal.be.api.common.ExperienceDto;
import com.github.butaji9l.jobportal.be.api.detail.ApplicantDetailDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantCreateDto;
import com.github.butaji9l.jobportal.be.api.request.ApplicantUpdateDto;
import com.github.butaji9l.jobportal.be.api.search.ApplicantQueryParams;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface ApplicantService extends
  CRUDService<ApplicantCreateDto, ApplicantDto, ApplicantUpdateDto, ApplicantDetailDto, ApplicantQueryParams> {

  /**
   * Generates a PDF file as byte array that contains CV of given applicant. CV is built based on
   * information about the applicant in the system.
   *
   * @param applicantId applicant id
   * @return byte array of PDF file
   */
  @NonNull
  ByteArrayInputStream generateCV(UUID applicantId);

  /**
   * Creates an experience entity and adds the new experience to given by id applicant.
   *
   * @param applicantId applicant id
   * @param payload     payload for creating an experience
   * @return updated applicant detail dto
   */
  @NonNull
  ApplicantDetailDto addExperience(@NonNull UUID applicantId, @NonNull ExperienceDto payload);

  /**
   * Deletes an experience entity and removes the experience from given by id applicant.
   *
   * @param applicantId  applicant id
   * @param experienceId id of experience to be deleted
   * @return updated applicant detail dto
   */
  @NonNull
  ApplicantDetailDto removeExperience(@NonNull UUID applicantId, @NonNull UUID experienceId);
}
