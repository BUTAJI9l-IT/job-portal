package muni.fi.cz.jobportal.domain;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "preferences")
@EqualsAndHashCode(of = "id")
public class UserPreferences {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private Boolean notificationsEnabled;
  @ManyToOne
  @JoinColumn(name = "lang")
  private Language language;
}
