package muni.fi.cz.jobportal.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * File entity class.
 *
 * @author Vitalii Bortsov
 */
@Getter
@Setter
@Table(name = "files")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class File {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name = "file_name")
  private String name;
  private String ext;
  @Lob
  private byte[] data;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "uploaded_by")
  private User uploadedBy;
}
