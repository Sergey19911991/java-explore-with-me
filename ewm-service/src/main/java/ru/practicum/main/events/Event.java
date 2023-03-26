package ru.practicum.main.events;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.Categorie;
import ru.practicum.main.location.Location;
import ru.practicum.main.user.User;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "annotation")
    private String annotation;
    @Column(name = "title")
    private String title;
    @Column(name = "event_date")
    private String eventDate;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "created_on")
    private String createdOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "published_on")
    private String publishedOn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_initiator")
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private Categorie category;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "state")
    private State state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_location")
    private Location location;
    @Column(name = "description")
    private String description;
    @Column(name = "views")
    private int views;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
}
