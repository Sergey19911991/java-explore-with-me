package ru.practicum.main.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class CommentDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String text;
}
