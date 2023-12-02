package muni.fi.cz.jobportal.domain;

import com.neovisionaries.i18n.LanguageCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

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
  @Column(name = "lang")
  @Enumerated(value = EnumType.STRING)
  private LanguageCode language = LanguageCode.en;
}
