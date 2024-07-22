package muni.fi.cz.jobportal.domain;

import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.*;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

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
  @FullTextField(name = NAME + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_category_id")
  private JobCategory category;
}
