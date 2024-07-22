package muni.fi.cz.jobportal.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import muni.fi.cz.jobportal.configuration.search.binder.ApplicationBinder;
import muni.fi.cz.jobportal.enums.ApplicationState;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

import static muni.fi.cz.jobportal.configuration.constants.SearchProperties.*;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

/**
 * Application entity class.
 *
 * @author Vitalii Bortsov
 */
@Indexed
@Getter
@Setter
@Table(name = "applications")
@Entity
@EqualsAndHashCode(of = "id")
@TypeBinding(binder = @TypeBinderRef(type = ApplicationBinder.class))
public class Application {

  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  @KeywordField(name = STATUS + SORT_SUFFIX, sortable = YES)
  @GenericField(name = STATUS)
  private ApplicationState state;

  @GenericField(name = APPLICATION_DATE)
  @GenericField(name = APPLICATION_DATE + SORT_SUFFIX, sortable = YES)
  private Instant date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicant")
  private Applicant applicant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_position")
  private JobPosition jobPosition;

}
