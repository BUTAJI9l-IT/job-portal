package com.github.butaji9l.jobportal.be.domain;

import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.APPLICATION_DATE;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.SORT_SUFFIX;
import static com.github.butaji9l.jobportal.be.configuration.constants.SearchProperties.STATUS;
import static org.hibernate.search.engine.backend.types.Sortable.YES;

import com.github.butaji9l.jobportal.be.configuration.search.binder.ApplicationBinder;
import com.github.butaji9l.jobportal.be.enums.ApplicationState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.TypeBinderRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.TypeBinding;

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
