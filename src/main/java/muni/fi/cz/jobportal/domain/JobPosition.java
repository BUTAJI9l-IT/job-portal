package muni.fi.cz.jobportal.domain;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.APPLICATION_DATE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.CITY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.COUNTRY;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.DESCRIPTION;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.NAME;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.RELEVANCE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATE;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.STATUS;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.configuration.search.binder.JobPositionBinder;
import muni.fi.cz.jobportal.enums.PositionState;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;

/**
 * Job Position entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@Entity
@Indexed
@Table(name = "job_positions")
@EqualsAndHashCode(of = "id")
@TypeBinding(binder = @TypeBinderRef(type = JobPositionBinder.class))
public class JobPosition {

  @Id
  @GeneratedValue
  private UUID id;
  @Enumerated(EnumType.STRING)
  @KeywordField(name = STATUS + SORT_SUFFIX, sortable = YES)
  @GenericField(name = STATUS)
  private PositionState status;
  @KeywordField(name = NAME + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = NAME + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String positionName;

  @KeywordField(name = COUNTRY + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = COUNTRY + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String country;
  @KeywordField(name = STATE + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = STATE + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String state;
  @KeywordField(name = CITY + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = CITY + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String city;

  private String contactEmail;
  @KeywordField(name = DESCRIPTION + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = DESCRIPTION + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String detail;

  @GenericField(name = RELEVANCE + SORT_SUFFIX, sortable = YES)
  private Instant created;
  private Instant lastUpdated;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToMany(mappedBy = "jobPosition", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private List<Application> applications;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "position_category",
    joinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "category", referencedColumnName = "id"))
  private List<JobCategory> jobCategories;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "applicant_saved_jobs",
    joinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "applicant", referencedColumnName = "id"))
  private List<JobPosition> applicantsSaved;
}
