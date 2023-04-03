package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categorie.dto.CategorieDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategorieController {
    private final CategorieService categorieService;

    @GetMapping()
    public List<CategorieDto> getCategories(@RequestParam(value = "from", defaultValue = "0") int from, @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Информация о категориях");
        return categorieService.getCategories(from, size);
    }

    @GetMapping(value = "/{catId}")
    public CategorieDto getCategorie(@PathVariable("catId") int catId) {
        log.info("Информация о категории с id = {}",catId);
        return categorieService.getCategorie(catId);
    }

}
