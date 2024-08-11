package com.github.butaji9l.jobportal.be.domain;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.EMAIL;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.FULLTEXT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.SORT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.USER_SCOPE;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.FULLTEXT_ANALYZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SORT_NORMALIZER;
import static com.github.butaji9l.jobportal.be.configuration.search.LuceneConfiguration.SUGGESTER;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import com.github.butaji9l.jobportal.be.configuration.search.binder.UserBinder;
import com.github.butaji9l.jobportal.be.enums.JobPortalScope;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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
  @FullTextField(name = EMAIL
    + FULLTEXT_SUFFIX, analyzer = FULLTEXT_ANALYZER, searchAnalyzer = SUGGESTER)
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

  @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "preferences_id")
  private UserPreferences preferences;

  public String getFullName() {
    if (name == null) {
      return lastName == null ? "" : lastName;
    }
    return lastName == null ? name : name + " " + lastName;
  }

  public UUID getNUI() {
    if (!JobPortalScope.ADMIN.equals(getScope())) {
      return getApplicant() == null ? getCompany().getId() : getApplicant().getId();
    }
    return getId();
  }

}
