package ru.practicum.main.compilations.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.events.Event;

import java.util.Set;

@Getter
@Setter
public class DtoCompilation {
    private Set<Event> events;
    private Boolean pinned;
    private String title;
    private Integer id;
}