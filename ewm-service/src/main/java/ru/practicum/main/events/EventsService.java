package ru.practicum.main.events;

import ru.practicum.main.events.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsService {
    NewEvent creatEvent(DtoEvent dtoEvent, int userId);

    List<EventUser> getEventUser(int id, int from, int size);

    NewEvent updateAdmin(UpdateAdmin updateAdmin, int id);

    List<EventsShortDto> getEvent(String text, int[] categories, String paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                         String onlyAvailable, Sort sort, int from, int size);

    NewEvent updateUser(UpdateAdmin updateAdmin, int userId, int eventId);

    NewEvent getEventById(int id);

    Event getUserEventById(int userId, int eventId);

    List<NewEvent> getAdminEvents(int size, int from, LocalDateTime rangeStart, LocalDateTime rangeEnd, int[] categories, int[] users,
                               String[] states);
}
