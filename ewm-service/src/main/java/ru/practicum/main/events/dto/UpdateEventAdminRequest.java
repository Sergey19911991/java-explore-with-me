package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.StateAction;
import ru.practicum.main.location.Location;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateEventAdminRequest {
    private int id;
    @Size(min = 20,max = 2000)
    private String annotation;
    @Size(min = 20,max = 7000)
    private String description;
    private Integer category;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    @Size(min = 3,max = 120)
    private String title;
}
