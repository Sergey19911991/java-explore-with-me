package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.CategorieDto;

@Getter
@Setter
public class EventsShortDto {
    private String annotation;
    private CategorieDto category;
    private Integer confirmedRequests;
    private String eventDate;
    private Integer id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Integer views;
}
