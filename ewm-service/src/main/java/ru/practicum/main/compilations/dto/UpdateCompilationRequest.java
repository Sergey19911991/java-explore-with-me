package ru.practicum.main.compilations.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateCompilationRequest {
    private Set<Integer> events;
    private Boolean pinned;
    private String title;
}
