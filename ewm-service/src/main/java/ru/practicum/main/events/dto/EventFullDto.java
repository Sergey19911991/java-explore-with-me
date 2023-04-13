package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.Categorie;
import ru.practicum.main.events.State;
import ru.practicum.main.location.Location;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventFullDto {
    private int id;
    private String annotation;
    private Categorie category;
    private String description;
    private boolean paid;
    private Integer participantLimit;
    private String title;
    private boolean requestModeration;
    private Location location;
    private String eventDate;
    private UserShortDto initiator;
    private LocalDateTime createdOn;
    private State state;
    private String publishedOn;
    private Long views;
    private Integer confirmedRequests;
}
