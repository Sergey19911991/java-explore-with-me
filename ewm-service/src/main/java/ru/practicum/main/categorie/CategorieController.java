package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categorie.dto.CategorieDto;
import ru.practicum.main.categorie.dto.NewCategoryDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CategorieDto creatCategorie(@RequestBody @Valid NewCategoryDto categorie) {
        log.info("Создана категория");
        return categorieService.creatCategorie(categorie);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{catId}")
    public void deletCategorie(@PathVariable int catId) {
        log.info("Удалена категория с id = {}",catId);
        categorieService.deletCategorie(catId);
    }


    @PatchMapping(value = "/{catId}")
    public CategorieDto updateCategorie(@RequestBody CategorieDto categorieDto, @PathVariable int catId) {
        log.info("Переименована категория с id = {}",catId);
        return categorieService.updateCategorie(categorieDto, catId);
    }

}
