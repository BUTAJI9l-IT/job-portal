package muni.fi.cz.jobportal.repository;

import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareCategoryEntity;
import static muni.fi.cz.jobportal.testutils.EntityUtils.prepareOccupationEntity;
import static org.assertj.core.api.Assertions.assertThat;

import muni.fi.cz.jobportal.AbstractIntegrationTest;
import muni.fi.cz.jobportal.domain.JobCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class JobCategoryRepositoryIT extends AbstractIntegrationTest {

  @Autowired
  private JobCategoryRepository jobCategoryRepository;
  @Autowired
  private OccupationRepository occupationRepository;

  @Test
  void findByOccupationNameTest() {
    final var catName1 = "Category1";
    final var occupationName1 = "Occupation1";
    final var catName2 = "Category2";
    final var occupationName2 = "Occupation2";
    occupationRepository.saveAndFlush(
      prepareOccupationEntity(jobCategoryRepository.saveAndFlush(prepareCategoryEntity(catName1)), occupationName1));
    occupationRepository.saveAndFlush(
      prepareOccupationEntity(jobCategoryRepository.saveAndFlush(prepareCategoryEntity(catName2)), occupationName2));

    assertThat(jobCategoryRepository.findByOccupationName(occupationName1)).isPresent().map(JobCategory::getName).get()
      .isEqualTo(catName1);
    assertThat(jobCategoryRepository.findByOccupationName(occupationName2)).isPresent().map(JobCategory::getName).get()
      .isEqualTo(catName2);
  }

}
