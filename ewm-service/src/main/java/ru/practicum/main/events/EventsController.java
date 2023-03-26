package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.hit.Hit;
import ru.practicum.main.hit.HitClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventsController {
    private final EventsServiceImpl eventsService;

    private final HitClient hitClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/users/{userId}/events")
    public NewEvent creatEvent(@RequestBody DtoEvent dtoEvent, @PathVariable int userId) {
        return eventsService.creatEvent(dtoEvent, userId);
    }

    @GetMapping(value = "/users/{userId}/events")
    public List<EventUser> getEventUser(@PathVariable int userId, @RequestParam(value = "from", defaultValue = "0") int from, @RequestParam(value = "size", defaultValue = "10") int size) {
        return eventsService.getEventUser(userId, from, size);
    }

    @PatchMapping(value = "/admin/events/{eventId}")
    public Event updateAdmin(@RequestBody UpdateAdmin updateAdmin, @PathVariable int eventId) {
        return eventsService.updateAdmin(updateAdmin, eventId);
    }

    @GetMapping(value = "/events")
    public List<Event> getEvent(@RequestParam(value = "text", required = false) String text,
                                @RequestParam(value = "categories", required = false) int[] categories,
                                @RequestParam(value = "paid", required = false) String paid,
                                @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                @RequestParam(value = "onlyAvailable", defaultValue = "false") String onlyAvailable,
                                @RequestParam(value = "sort", defaultValue = "EVENT_DATE") Sort sort,
                                @RequestParam(value = "from", defaultValue = "0") int from,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                HttpServletRequest request) {
        Hit hit = new Hit();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        hit.setTimestamp(LocalDateTime.now().format(formatter));
        hit.setIp(request.getRemoteAddr());
        hit.setUri(request.getRequestURI());
        hit.setApp("ewm-main-service");
        hitClient.createHit(hit);
        return eventsService.getEvent(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    ;

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public Event updateUser(@RequestBody UpdateAdmin updateAdmin, @PathVariable(value = "userId") int userId, @PathVariable(value = "eventId") int eventId) {
        return eventsService.updateUser(updateAdmin, userId, eventId);
    }

    @GetMapping(value = "/events/{id}")
    public Event getEventById(@PathVariable int id, HttpServletRequest request) {
        Hit hit = new Hit();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        hit.setTimestamp(LocalDateTime.now().format(formatter));
        hit.setIp(request.getRemoteAddr());
        hit.setUri(request.getRequestURI());
        hit.setApp("ewm-main-service");
        hitClient.createHit(hit);
        return eventsService.getEventById(id);
    }

    @GetMapping(value = "/users/{userId}/events/{eventId}")
    public Event getEventUser(@PathVariable int userId, @PathVariable int eventId) {
        return eventsService.getUserEventById(userId, eventId);
    }

    @GetMapping(value = "/admin/events")
    public List<Event> getEventAdmin(@RequestParam(value = "from", defaultValue = "0") int from,
                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                     @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                     @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                     @RequestParam(value = "categories") int[] categories,
                                     @RequestParam(value = "users") int[] users,
                                     @RequestParam(value = "states", required = false) String[] states) {
        return eventsService.getAdminEvents(size, from, rangeStart, rangeEnd, categories, users, states);
    }
}
