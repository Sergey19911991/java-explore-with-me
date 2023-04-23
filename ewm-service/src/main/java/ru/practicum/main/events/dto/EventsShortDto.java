package ru.practicum.main.events.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.categorie.dto.CategorieDto;
import ru.practicum.main.comment.dto.NewComment;

import java.util.List;

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
    private Long views;
    private List<NewComment> comments;
}
