package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationServiceImpl compilationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/admin/compilations")
    public Compilation creatCompilation(@RequestBody DtoCompilation dtoCompilation) {
        return compilationService.creatCompilation(dtoCompilation);
    }

    @GetMapping(value = "/compilations/{compId}")
    public Compilation getCompilation(@PathVariable int compId) {
        return compilationService.getCompilation(compId);
    }

    @GetMapping(value = "/compilations")
    public List<Compilation> getCompilations(@RequestParam(value = "from", defaultValue = "0") int from,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestParam(value = "pinned", required = false) Boolean pinned) {
        return compilationService.getCompilations(from, size, pinned);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/admin/compilations/{compId}")
    public void deletCompilation(@PathVariable int compId) {
        compilationService.deletCompilation(compId);
    }

    @PatchMapping(value = "/admin/compilations/{compId}")
    public Compilation updateCompilation(@PathVariable int compId, @RequestBody DtoCompilation dtoCompilation) {
        return compilationService.updateCompilation(compId, dtoCompilation);
    }

}
