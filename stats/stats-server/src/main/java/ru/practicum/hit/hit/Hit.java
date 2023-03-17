package ru.practicum.hit.hit;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "hit")
@Data
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
       private String timestamp;
}
