package ru.practicum.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilations.dto.DtoCompilation;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping(value = "/{compId}")
    public DtoCompilation getCompilation(@PathVariable int compId) {
        log.info("Информация о подборке событий с id = {}",compId);
        return compilationService.getCompilation(compId);
    }

    @GetMapping()
    public List<DtoCompilation> getCompilations(@RequestParam(value = "from", defaultValue = "0") int from,
                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                             @RequestParam(value = "pinned", required = false) Boolean pinned) {
        log.info("Информация о подборках событий");
        return compilationService.getCompilations(from, size, pinned);
    }
}
