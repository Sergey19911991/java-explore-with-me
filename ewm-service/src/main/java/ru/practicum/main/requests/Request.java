package ru.practicum.main.requests;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.Event;
import ru.practicum.main.user.User;

import javax.persistence.*;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "created")
    private String created;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event", nullable = false)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester", nullable = false)
    private User requester;
    @Column(name = "status")
    private String status;
}
