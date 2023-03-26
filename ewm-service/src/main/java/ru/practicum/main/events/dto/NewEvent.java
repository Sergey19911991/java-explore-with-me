package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.Categorie;
import ru.practicum.main.events.State;
import ru.practicum.main.location.Location;
import ru.practicum.main.user.User;

@Getter
@Setter
public class NewEvent {
    private int id;
    private String annotation;
    private Categorie category;
    private String description;
    private Boolean paid;
    private int participantLimit;
    private String title;
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
    private User initiator;
    private String createdOn;
    private State state;
}

