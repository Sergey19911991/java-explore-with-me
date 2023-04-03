package ru.practicum.main.categorie;

import ru.practicum.main.categorie.dto.CategorieDto;
import ru.practicum.main.categorie.dto.NewCategoryDto;

import java.util.List;

public interface CategorieService {
    CategorieDto creatCategorie(NewCategoryDto newCategoryDto);

    void deletCategorie(int catId);

    List<CategorieDto> getCategories(int from, int size);

    CategorieDto getCategorie(int id);

    CategorieDto updateCategorie(CategorieDto categorieDto, int catId);
}
