package muni.fi.cz.jobportal.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.enums.CompanyNumberOfEmployees;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.*;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

/**
 * Company entity class.
 *
 * @author Vitalii Bortsov
 */
@Indexed
@Getter
@Setter
@Table(name = "companies")
@Entity
@EqualsAndHashCode(of = "id")
public class Company {

  @Id
  @GeneratedValue
  private UUID id;
  @KeywordField(name = NAME + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = NAME + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String companyName;
  private String companyLink;
  @KeywordField(name = DESCRIPTION + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = DESCRIPTION + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String description;

  @Enumerated(EnumType.STRING)
  @KeywordField(name = COMPANY_SIZE + SORT_SUFFIX, sortable = YES)
  @GenericField(name = COMPANY_SIZE)
  private CompanyNumberOfEmployees companySize;

  @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private List<JobPosition> jobPositions;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
