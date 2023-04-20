package bts.bu136.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BackupFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pref;
    private String path;
    private String remote;

    public BackupFolder(String pref, String path, String remote) {
        this.pref = pref;
        this.path = path;
        this.remote = remote;
    }
}
