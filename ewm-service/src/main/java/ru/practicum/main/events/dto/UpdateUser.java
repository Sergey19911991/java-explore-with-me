package ru.practicum.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.StateAction;
import ru.practicum.main.location.Location;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateUser {
    private String annotation;
    private Integer category;
    private String description;
    private Boolean paid;
    private int participantLimit;
    private String title;
    private Boolean requestModeration;
    private Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private StateAction stateAction;
}
