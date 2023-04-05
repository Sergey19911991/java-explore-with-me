package ru.practicum.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class HitDto {
    private String app;
    private String uri;
    private Long hits;
}
