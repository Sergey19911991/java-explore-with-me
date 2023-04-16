package ru.practicum.main.requests.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.requests.Status;

import java.util.List;

@Getter
@Setter
public class DtoRequest {
    private List<Integer> requestIds;
    private Status status;
}
