package ru.practicum.main.compilations.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class NewCompilationDto {
    private Set<Integer> events;
    @Value("false")
    private Boolean pinned;
    @NotBlank
    private String title;
}
