package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Compilation creatCompilation(@RequestBody @Valid DtoCompilation dtoCompilation) {
        return compilationService.creatCompilation(dtoCompilation);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{compId}")
    public void deletCompilation(@PathVariable int compId) {
        compilationService.deletCompilation(compId);
    }

    @PatchMapping(value = "/{compId}")
    public Compilation updateCompilation(@PathVariable int compId, @RequestBody DtoCompilation dtoCompilation) {
        return compilationService.updateCompilation(compId, dtoCompilation);
    }

}
