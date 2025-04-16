package ro.sisc.ttj.inttjence.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODELS")
@Getter
@Setter
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String version;

    @Column
    private String url;

    @Column
    private String apiKey;

    @Column
    private String type;
}
