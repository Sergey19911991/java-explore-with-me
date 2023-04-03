package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.dto.CategorieDto;

@Getter
@Setter
public class EventUser {
    private int id;
    private String title;
    private String annotation;
    private CategorieDto category;
    private Boolean paid;
    private String eventDate;
}
