package ru.practicum.main.categorie;

import java.util.List;

public interface CategorieService {
    Categorie creatCategorie(DtoCategorie dtoCategorie);

    void deletCategorie(int catId);

    List<Categorie> getCategories(int from, int size);

    Categorie getCategorie(int id);

    Categorie updateCategorie(DtoCategorie dtoCategorie, int catId);
}
