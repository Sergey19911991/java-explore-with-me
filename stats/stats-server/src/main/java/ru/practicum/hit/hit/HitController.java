package ru.practicum.hit.hit;

import dto.HitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {
    private final HitServiceImpl hitService;

    @PostMapping(value = "/hit")
    public Hit creatHit(@RequestBody Hit hit) {
        return hitService.creatHit(hit);
    }

    @GetMapping(value="/stats")
    public List<HitDto> getHits(@RequestParam (value = "start") String start, @RequestParam (value = "end") String end,
                                @RequestParam (value = "uris") String[] uri, @RequestParam (value = "unique", defaultValue = "false") boolean unique) {
           return hitService.getHits(start,end,uri, unique);
    }
}
