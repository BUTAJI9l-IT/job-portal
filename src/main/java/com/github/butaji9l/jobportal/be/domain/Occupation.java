package com.github.butaji9l.jobportal.be.domain;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.NAME;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.SORT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

/**
 * Occupation entity class.
 *
 * @author Vitalii Bortsov
 */
@Indexed
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_occupations")
@Entity
@EqualsAndHashCode(of = "id")
public class Occupation {

  @Id
  @GeneratedValue
  private UUID id;

  @KeywordField(name = NAME + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = NAME
    + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_category_id")
  private JobCategory category;
}
