package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.location.Location;

@Getter
@Setter
public class UpdateEventAdminRequest {
    private int id;
    private String annotation;
    private String description;
    private Integer category;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String stateAction;
    private String title;
}
