package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.main.location.Location;

import javax.validation.constraints.Size;


@Getter
@Setter
public class DtoEvent {
    @Size(min = 20,max = 2000)
    private String annotation;
    private int category;
    @Size(min = 20,max = 7000)
    private String description;
    private Boolean paid;
    @Value("0")
    private int participantLimit;
    @Size(min = 3,max = 120)
    private String title;
    @Value("true")
    private Boolean requestModeration;
    private Location location;
    private String eventDate;
}
