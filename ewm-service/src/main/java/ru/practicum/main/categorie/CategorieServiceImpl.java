package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.RequestException;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    private final EventsRepository eventsRepository;

    @Override
    public Categorie creatCategorie(DtoCategorie dtoCategorie) {
        if (dtoCategorie.getName() != null) {
            List<Categorie> categories = categorieRepository.getName(dtoCategorie.getName());
            if (categories.size() == 0) {
                Categorie categorie = new Categorie();
                categorie.setName(dtoCategorie.getName());
                return categorieRepository.save(categorie);
            } else {
                throw new ConflictException("");
            }
        } else {
            throw new RequestException("Неправильное тело запроса");
        }
    }

    @Override
    public void deletCategorie(int catId) {
        Categorie categorie = categorieRepository.findById(catId).get();
        if (eventsRepository.getEventsByCategoriy(catId).size() == 0) {
            categorieRepository.deleteById(catId);
        } else {
            throw new ConflictException("");
        }
    }

    @Override
    public List<Categorie> getCategories(int from, int size) {
        return categorieRepository.getCategories(from, size);
    }

    @Override
    public Categorie getCategorie(int id) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        if (categorie != null) {
            return categorie;
        } else {
            throw new NotFoundException("");
        }
    }

    @Override
    public Categorie updateCategorie(DtoCategorie dtoCategorie, int catId) {
        Categorie categorie = categorieRepository.findById(catId).get();
        if (dtoCategorie.getName() != null) {
            List<Categorie> categories = categorieRepository.getName(dtoCategorie.getName());
            if (categories.size() == 0) {
                categorie.setName(dtoCategorie.getName());
                return categorieRepository.save(categorie);
            } else {
                throw new ConflictException("");
            }
        } else {
            throw new RequestException("");
        }
    }

}
