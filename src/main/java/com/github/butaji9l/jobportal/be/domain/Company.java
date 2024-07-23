package com.github.butaji9l.jobportal.be.domain;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.COMPANY_SIZE;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.DESCRIPTION;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.SORT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import com.github.butaji9l.jobportal.be.enums.CompanyNumberOfEmployees;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

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
  @FullTextField(name = NAME
    + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String companyName;
  private String companyLink;
  @KeywordField(name = DESCRIPTION + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = DESCRIPTION
    + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String description;

  @Enumerated(EnumType.STRING)
  @KeywordField(name = COMPANY_SIZE + SORT_SUFFIX, sortable = YES)
  @GenericField(name = COMPANY_SIZE)
  private CompanyNumberOfEmployees companySize;

  @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST,
    CascadeType.REMOVE}, fetch = FetchType.LAZY)
  private List<JobPosition> jobPositions;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
