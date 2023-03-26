package ru.practicum.main.compilations;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DtoCompilation {
    private List<Integer> events;
    private Boolean pinned;
    private String title;
}