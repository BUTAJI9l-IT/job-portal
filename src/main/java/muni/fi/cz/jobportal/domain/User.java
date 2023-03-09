package muni.fi.cz.jobportal.domain;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.EMAIL;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.SORT_SUFFIX;
import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.USER_SCOPE;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static muni.fi.cz.jobportal.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.configuration.search.binder.UserBinder;
import muni.fi.cz.jobportal.enums.JobPortalScope;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;

/**
 * User entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@Entity
@Indexed
@Table(name = "jp_users")
@EqualsAndHashCode(of = "id")
@TypeBinding(binder = @TypeBinderRef(type = UserBinder.class))
public class User {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "email", unique = true)
  @KeywordField(name = EMAIL + SORT_SUFFIX, sortable = YES, normalizer = SORT_NORMALIZER)
  @FullTextField(name = EMAIL, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
  private String email;
  @Column(name = "password")
  private String password;

  private String name;
  private String lastName;

  @OneToOne(mappedBy = "uploadedBy", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  @JoinColumn(name = "avatar_file")
  private File avatar;

  @Enumerated(EnumType.STRING)
  @Column(name = "scope")
  @KeywordField(name = USER_SCOPE + SORT_SUFFIX, sortable = YES)
  @GenericField(name = USER_SCOPE)
  private JobPortalScope scope;

  @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "applicant_id")
  private Applicant applicant;

  @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "company_id")
  private Company company;

  public String getFullName() {
    if (name == null) {
      return lastName == null ? "" : lastName;
    }
    return lastName == null ? name : name + " " + lastName;
  }

}
