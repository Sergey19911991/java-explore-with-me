package ru.practicum.hit.hit;

import dto.DtoInletHit;
import dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitService hitService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/hit")
    public HitDto creatHit(@RequestBody DtoInletHit hit) {
        log.info("Сохранена информация о просмотре событий");
        return hitService.creatHit(hit);
    }

    @GetMapping(value = "/stats")
    public List<HitDto> getHits(@RequestParam(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start, @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                @RequestParam(value = "uris",required = false) String[] uri, @RequestParam(value = "unique", defaultValue = "false") boolean unique) {
        log.info("Информация о просмотре событий");
        return hitService.getHits(start, end, uri, unique);
    }
}
