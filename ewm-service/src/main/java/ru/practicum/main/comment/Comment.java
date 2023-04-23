package ru.practicum.main.comment;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.Event;
import ru.practicum.main.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "text")
    private String text;
    @Column(name = "created")
    LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event")
    private Event event;
}
