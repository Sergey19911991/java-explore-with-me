package ru.practicum.main.categorie;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DtoCategorie {
    @NotBlank
    private String name;
}
