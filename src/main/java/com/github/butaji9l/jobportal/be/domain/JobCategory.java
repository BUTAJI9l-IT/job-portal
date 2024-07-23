package com.github.butaji9l.jobportal.be.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Job Category entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@NoArgsConstructor
@Table(name = "job_categories")
@Entity
@EqualsAndHashCode(of = "id")
public class JobCategory {

  public JobCategory(UUID id, String name, List<Occupation> occupations) {
    this.id = id;
    this.name = name;
    this.occupations = occupations;
  }

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Occupation> occupations;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "position_category", joinColumns = @JoinColumn(name = "category", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "job_position", referencedColumnName = "id"))
  private List<JobPosition> positions;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "experience_category", joinColumns = @JoinColumn(name = "category", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "experience", referencedColumnName = "id"))
  private List<JobCategory> experiences;
}
