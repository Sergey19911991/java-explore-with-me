package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieServiceImpl categorieService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Categorie creatCategorie(@RequestBody @Valid DtoCategorie categorie) {
        return categorieService.creatCategorie(categorie);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{catId}")
    public void deletCategorie(@PathVariable int catId) {
        categorieService.deletCategorie(catId);
    }


    @PatchMapping(value = "/{catId}")
    public Categorie updateCategorie(@RequestBody DtoCategorie dtoCategorie, @PathVariable int catId) {
        return categorieService.updateCategorie(dtoCategorie, catId);
    }

}
