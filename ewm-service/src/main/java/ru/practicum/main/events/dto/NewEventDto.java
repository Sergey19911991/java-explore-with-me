package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import ru.practicum.main.location.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NewEventDto {
    @NotBlank
    @Size(min = 20,max = 2000)
    private String annotation;
    private int category;
    @NotBlank
    @Size(min = 20,max = 7000)
    private String description;
    private boolean paid;
    @Value("0")
    @PositiveOrZero
    private Integer participantLimit;
    @NotBlank
    @Size(min = 3,max = 120)
    private String title;
    @Value("true")
    private Boolean requestModeration;
    @NotNull
    private Location location;
    @NotBlank
    private String eventDate;
}
