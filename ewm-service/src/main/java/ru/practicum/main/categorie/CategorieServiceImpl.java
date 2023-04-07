package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.categorie.dto.CategorieDto;
import ru.practicum.main.categorie.dto.NewCategoryDto;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.RequestException;


import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    private final EventsRepository eventsRepository;

    private final MappingCategory mappingCategory;

    @Override
    public CategorieDto creatCategorie(NewCategoryDto newCategoryDto) {
        List<Categorie> categories = categorieRepository.getName(newCategoryDto.getName());
        if (categories.size() == 0) {
            Categorie categorie = mappingCategory.categorieNewCategoryDto(newCategoryDto);
            log.info("Создана категория");
            categorieRepository.save(categorie);
            return mappingCategory.categorieDtoCategorie(categorie);
        } else {
            log.error("Нельзя создать категорию с занятым именем!");
            throw new ConflictException("Нельзя создать категорию с занятым именем!");
        }
    }

    @Override
    public void deletCategorie(int catId) {
        if (eventsRepository.getEventsByCategoriy(catId).size() == 0) {
            log.info("Удалена категория с id = {}", catId);
            categorieRepository.deleteById(catId);
        } else {
            log.error("Нельзя удалить категорию, к которой относятся события");
            throw new ConflictException("Нельзя удалить категорию, к которой относятся события");
        }
    }

    @Override
    public List<CategorieDto> getCategories(int from, int size) {
        log.info("Информация о категориях");
        List<Categorie> categories = categorieRepository.getCategories(from, size);
        List<CategorieDto> categorieDtos = new ArrayList<>();
        for (Categorie categorie : categories) {
            categorieDtos.add(mappingCategory.categorieDtoCategorie(categorie));
        }
        return categorieDtos;
    }

    @Override
    public CategorieDto getCategorie(int id) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new NotFoundException("Категория не найдена"));
        log.info("Информация о категории с id = {}", id);
        return mappingCategory.categorieDtoCategorie(categorie);
    }

    @Override
    public CategorieDto updateCategorie(CategorieDto categorieDto, int catId) {
        Categorie categorie = categorieRepository.findById(catId).orElseThrow(() -> new NotFoundException("Категория не найдена"));
        if (categorieDto.getName() != null && !categorieDto.getName().isBlank()) {
            List<Categorie> categories = categorieRepository.getName(categorieDto.getName());
            if (categories.size() == 0 || categorie.getName().equals(categorieDto.getName())) {
                categorie = mappingCategory.dtoCategorie(categorieDto, categorie);
                log.info("Переименована категория с id = {}", catId);
                categorieRepository.save(categorie);
                return mappingCategory.categorieDtoCategorie(categorie);
            } else {
                log.error("Попытка создать категорию с занятым именем!");
                throw new ConflictException("Попытка создать категорию с занятым именем!");
            }
        } else {
            log.error("Нельзя создать категорию без имени!");
            throw new RequestException("Нельзя создать категорию без имени!");
        }
    }

}
