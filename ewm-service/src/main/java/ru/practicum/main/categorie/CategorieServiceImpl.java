package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.ConflictException;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Override
    public Categorie creatCategorie(dtoCategorie dtoCategorie) {
        log.info("В сервисе категорий");
        List<Categorie> categories = categorieRepository.getName(dtoCategorie.getName());
        if (categories.size() == 0) {
            log.info("В сервисе категорий");
            Categorie categorie = new Categorie();
            categorie.setName(dtoCategorie.getName());
            return categorieRepository.save(categorie);
        } else {
            throw new ConflictException("");
        }

    }

    @Override
    public void deletCategorie(int catId) {
        categorieRepository.deleteById(catId);
    }

    @Override
    public List<Categorie> getCategories(int from, int size) {
        log.info("В сервисе GET");
        return categorieRepository.getCategories(from, size);
    }

    @Override
    public Categorie getCategorie(int id) {
        return categorieRepository.getCategorie(id);
    }


}
