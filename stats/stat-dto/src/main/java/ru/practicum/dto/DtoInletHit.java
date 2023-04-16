package ru.practicum.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class DtoInletHit {
    @Value("${name-module}")
    private String app;
    private String uri;
    private Long hits;
    private String ip;
    private String timestamp;
}
