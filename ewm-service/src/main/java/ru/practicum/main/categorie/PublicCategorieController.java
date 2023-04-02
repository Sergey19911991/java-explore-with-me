package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategorieController {
    private final CategorieServiceImpl categorieService;

    @GetMapping()
    public List<Categorie> getCategories(@RequestParam(value = "from", defaultValue = "0") int from, @RequestParam(value = "size", defaultValue = "10") int size) {
        return categorieService.getCategories(from, size);
    }

    @GetMapping(value = "/{catId}")
    public Categorie getCategorie(@PathVariable("catId") int catId) {
        return categorieService.getCategorie(catId);
    }

}
