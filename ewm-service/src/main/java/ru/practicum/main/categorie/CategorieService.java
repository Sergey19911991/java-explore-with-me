package ru.practicum.main.categorie;

import java.net.URI;
import java.util.List;

public interface CategorieService {
        Categorie creatCategorie(dtoCategorie dtoCategorie);

        void deletCategorie (int catId);

        List<Categorie> getCategories(int from, int size);

        Categorie getCategorie(int id);
}
