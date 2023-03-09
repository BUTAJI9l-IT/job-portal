package muni.fi.cz.jobportal.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Refresh Token entity class.
 *
 * @author Vitalii Bortsov
 */
@EqualsAndHashCode(of = "token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

  @Id
  private String token;

  private Instant expires;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "jp_user")
  private User user;
}
