package ru.practicum.main.requests.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipationReauestDto {
    private String created;
    private Integer event;
    private Integer id;
    private Integer requester;
    private String status;
}
