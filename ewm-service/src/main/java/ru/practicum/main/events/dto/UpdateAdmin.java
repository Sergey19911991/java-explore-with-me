package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.StateAction;
import ru.practicum.main.location.Location;

@Getter
@Setter
public class UpdateAdmin {
    private String annotation;
    private Integer category;
    private String description;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
    private StateAction stateAction;
}
