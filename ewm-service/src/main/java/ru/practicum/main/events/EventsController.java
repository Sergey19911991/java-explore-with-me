package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.dto.NewEvent;
import ru.practicum.main.events.dto.UpdateAdmin;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventsController {
    private final EventsService eventsService;

    @PatchMapping(value = "/{eventId}")
    public NewEvent updateAdmin(@RequestBody UpdateAdmin updateAdmin, @PathVariable int eventId) {
        return eventsService.updateAdmin(updateAdmin, eventId);
    }


    @GetMapping()
    public List<NewEvent> getEventAdmin(@RequestParam(value = "from", defaultValue = "0") int from,
                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                     @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                     @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                     @RequestParam(value = "categories",required = false) int[] categories,
                                     @RequestParam(value = "users",required = false) int[] users,
                                     @RequestParam(value = "states", required = false) String[] states) {
        return eventsService.getAdminEvents(size, from, rangeStart, rangeEnd, categories, users, states);
    }
}
