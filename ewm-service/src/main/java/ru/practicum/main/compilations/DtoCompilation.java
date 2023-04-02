package ru.practicum.main.compilations;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class DtoCompilation {
    private Set<Integer> events;
    private Boolean pinned;
    @NotBlank
    private String title;
}