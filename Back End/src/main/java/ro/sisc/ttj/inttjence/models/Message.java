package ro.sisc.ttj.inttjence.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;
    @Lob
    @Column
    private String content;
    @Column
    private String model;
    @Column
    private LocalDateTime insertTimestamp;
}
