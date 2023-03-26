package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.location.Location;


@Getter
@Setter
public class DtoEvent {
    private String annotation;
    private int category;
    private String description;
    private Boolean paid;
    private int participantLimit;
    private String title;
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
}
