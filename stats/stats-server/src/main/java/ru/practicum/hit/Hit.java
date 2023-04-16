package ru.practicum.hit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "hit")
@Getter
@Setter
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hit")
    private int id;
    @Column(name = "ip")
    private String ip;
    @Column(name = "uri")
    private String uri;
    @Column(name = "app")
    private String app;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
