package ru.practicum.main.events;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventsShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsService {
    EventFullDto creatEvent(NewEventDto dtoEvent, int userId);

    List<EventsShortDto> getEventUser(int id, int from, int size);

    EventFullDto updateAdmin(UpdateEventAdminRequest updateAdmin, int id);

    List<EventsShortDto> getEvent(String text, int[] categories, String paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                         String onlyAvailable, Sort sort, int from, int size);

    EventFullDto updateUser(UpdateEventAdminRequest updateAdmin, int userId, int eventId);

    EventFullDto getEventById(int id);

    EventFullDto getUserEventById(int userId, int eventId);

    List<EventFullDto> getAdminEvents(int size, int from, LocalDateTime rangeStart, LocalDateTime rangeEnd, int[] categories, int[] users,
                               String[] states);
}
