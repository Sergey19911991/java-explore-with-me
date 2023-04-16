package ru.practicum.main.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewUserRequest {
    @NotBlank
    private String name;
    @Email
    @NotEmpty
    private String email;
}
