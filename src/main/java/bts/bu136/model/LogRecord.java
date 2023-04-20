package bts.bu136.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pref;
    private String message;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    public LogRecord(String pref, String message, Type type, LocalDateTime dateTime) {
        this.pref = pref;
        this.message = message;
        this.type = type;
        this.dateTime = dateTime;
    }

    public enum Type {
        INFO, ERROR
    }
}
