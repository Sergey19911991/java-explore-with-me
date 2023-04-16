package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilations.dto.DtoCompilation;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public DtoCompilation creatCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("Создана подборка событий");
        return compilationService.creatCompilation(newCompilationDto);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{compId}")
    public void deletCompilation(@PathVariable int compId) {
        log.info("Удалена подборка событий с id = {}",compId);
        compilationService.deletCompilation(compId);
    }

    @PatchMapping(value = "/{compId}")
    public DtoCompilation updateCompilation(@PathVariable int compId, @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("Заменена подборка событий с id = {}",compId);
        return compilationService.updateCompilation(compId, updateCompilationRequest);
    }

}
