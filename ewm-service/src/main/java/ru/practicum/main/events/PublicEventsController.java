package ru.practicum.main.events;

import hit.HitClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.dto.EventsShortDto;
import ru.practicum.main.events.dto.NewEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@ComponentScan("hit")
public class PublicEventsController {
    private final EventsService eventsService;

    private final HitClient hitClient;

    @GetMapping()
    public List<EventsShortDto> getEvent(@RequestParam(value = "text", required = false) String text,
                                         @RequestParam(value = "categories", required = false) int[] categories,
                                         @RequestParam(value = "paid", required = false) String paid,
                                         @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam(value = "onlyAvailable", defaultValue = "false") String onlyAvailable,
                                         @RequestParam(value = "sort", defaultValue = "EVENT_DATE") Sort sort,
                                         @RequestParam(value = "from", defaultValue = "0") int from,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         HttpServletRequest request, @Value("${DtoInletHit.app}") String app) {
        hitClient.createHit(hitClient.mappingHitDtoClient(request, app));
        log.info("Информация о событиях");
        return eventsService.getEvent(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping(value = "/{id}")
    public NewEvent getEventById(@PathVariable int id, HttpServletRequest request, @Value("${DtoInletHit.app}") String app) {
        hitClient.createHit(hitClient.mappingHitDtoClient(request, app));
        log.info("Информация о событии с id = {}", id);
        return eventsService.getEventById(id);
    }
}
