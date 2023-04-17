package ru.practicum.main.comment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.user.dto.UserDto;

@Getter
@Setter
public class NewComment {
    private int id;
    private String text;
    private String created;
    private UserDto user;
    private Integer eventId;
}
