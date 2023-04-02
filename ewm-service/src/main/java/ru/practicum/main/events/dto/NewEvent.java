package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.Categorie;
import ru.practicum.main.events.State;
import ru.practicum.main.location.Location;

import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewEvent {
    private int id;
    private String annotation;
    private Categorie category;
    private String description;
    private Boolean paid;
    @PositiveOrZero
    private int participantLimit;
    private String title;
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
    private UserShortDto initiator;
    private LocalDateTime createdOn;
    private State state;
    private String publishedOn;
    private Integer views;
    private Integer confirmedRequests;
}

