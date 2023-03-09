package muni.fi.cz.jobportal.domain;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CITY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COUNTRY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATE;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.configuration.search.binder.ApplicantBinder;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;

/**
 * Applicant entity class.
 *
 * @author Vitalii Bortsov
 */
@Indexed
@Getter
@Setter
@Table(name = "applicants")
@Entity
@EqualsAndHashCode(of = "id")
@TypeBinding(binder = @TypeBinderRef(type = ApplicantBinder.class))
public class Applicant {

  @Id
  @GeneratedValue
  private UUID id;
  @KeywordField(name = COUNTRY + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = COUNTRY, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String country;
  @KeywordField(name = STATE + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = STATE, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String state;
  @KeywordField(name = CITY + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = CITY, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String city;

  private String phone;
  private String profile;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "applicant", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private List<Experience> experiences;

  @OneToMany(mappedBy = "applicant", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private List<Application> applications;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "applicant_saved_jobs",
      joinColumns = @JoinColumn(name = "applicant", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"))
  private List<JobPosition> savedJobs;
}
