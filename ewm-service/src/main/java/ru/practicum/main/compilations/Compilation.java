package ru.practicum.main.compilations;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compilation")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "pinned")
    private Boolean pinned;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Event_Compolation",
            joinColumns = {@JoinColumn(name = "id_compilation")},
            inverseJoinColumns = {@JoinColumn(name = "id")}
    )
    Set<Event> events;

}
