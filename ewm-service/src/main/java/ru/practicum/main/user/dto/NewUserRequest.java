package ru.practicum.main.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    private String name;
    private String email;
}
