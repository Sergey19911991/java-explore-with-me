package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.dto.DtoEvent;
import ru.practicum.main.events.dto.EventUser;
import ru.practicum.main.events.dto.NewEvent;
import ru.practicum.main.events.dto.UpdateAdmin;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UsersEventsController {
    private final EventsService eventsService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}/events")
    public NewEvent creatEvent(@RequestBody @Valid DtoEvent dtoEvent, @PathVariable int userId) {
        log.info("Создано событие");
        return eventsService.creatEvent(dtoEvent, userId);
    }

    @GetMapping(value = "/{userId}/events")
    public List<EventUser> getEventUser(@PathVariable int userId, @RequestParam(value = "from", defaultValue = "0") int from, @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Информация о событиях");
        return eventsService.getEventUser(userId, from, size);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}")
    public NewEvent updateUser(@RequestBody UpdateAdmin updateAdmin, @PathVariable(value = "userId") int userId, @PathVariable(value = "eventId") int eventId) {
        log.info("Перезаписано событие с id = {}",eventId);
        return eventsService.updateUser(updateAdmin, userId, eventId);
    }


    @GetMapping(value = "/{userId}/events/{eventId}")
    public Event getEventUser(@PathVariable int userId, @PathVariable int eventId) {
        log.info("Информация о событии с id = {}",eventId);
        return eventsService.getUserEventById(userId, eventId);
    }
}
