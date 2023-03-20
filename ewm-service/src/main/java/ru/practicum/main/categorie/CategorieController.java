package ru.practicum.main.categorie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieServiceImpl categorieService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value="/admin/categories")
    public Categorie creatCategorie(@RequestBody dtoCategorie categorie){
        log.info("В контроллере категорий");
        return categorieService.creatCategorie(categorie);
    };


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/admin/categories/{catId}")
    public void deletCategorie (@PathVariable("catId") int catId) {categorieService.deletCategorie(catId);
    }

    @GetMapping(value="/categories")
    public List<Categorie> getCategories( @RequestParam(value = "from",defaultValue ="0") int from, @RequestParam(value = "size", defaultValue = "10") int size) {
        return categorieService.getCategories(from,size);
    }

    @GetMapping(value="/categories/{catId}")
    public Categorie getCategorie(@PathVariable ("catId")int catId) {
        return categorieService.getCategorie(catId);
    }

}
