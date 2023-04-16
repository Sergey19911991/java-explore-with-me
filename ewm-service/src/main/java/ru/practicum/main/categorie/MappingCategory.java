package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.categorie.dto.CategorieDto;
import ru.practicum.main.categorie.dto.NewCategoryDto;

@RequiredArgsConstructor
@Service
public class MappingCategory {
    public Categorie categorieNewCategoryDto(NewCategoryDto newCategoryDto) {
        Categorie categorie = new Categorie();
        categorie.setName(newCategoryDto.getName());
        return categorie;
    }

    public CategorieDto categorieDtoCategorie(Categorie categorie) {
        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(categorie.getId());
        categorieDto.setName(categorie.getName());
        return categorieDto;
    }

    public Categorie dtoCategorie(CategorieDto categorieDto, Categorie categorie) {
        categorie.setName(categorieDto.getName());
        return categorie;
    }
}
