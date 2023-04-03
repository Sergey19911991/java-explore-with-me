package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.StateAction;
import ru.practicum.main.location.Location;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateAdmin {
    @Size(min = 20,max = 2000)
    private String annotation;
    private Integer category;
    @Size(min = 20,max = 7000)
    private String description;
    private Boolean paid;
    private Integer participantLimit;
    @Size(min = 3,max = 120)
    private String title;
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
    private StateAction stateAction;
}
