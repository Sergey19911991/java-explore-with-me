package ru.practicum.main.events;

import ru.practicum.main.events.dto.*;

import java.util.List;

public interface EventsService {
    NewEvent creatEvent(DtoEvent dtoEvent, int userId);

    List<EventUser> getEventUser(int id, int from, int size);

    Event updateAdmin(UpdateAdmin updateAdmin, int id);

    List<Event> getEvent(String text, int[] categories, String paid, String rangeStart, String rangeEnd,
                         String onlyAvailable, Sort sort, int from, int size);

    Event updateUser(UpdateAdmin updateAdmin, int userId, int eventId);

    Event getEventById(int id);

    Event getUserEventById(int userId, int eventId);

    List<Event> getAdminEvents(int size, int from, String rangeStart, String rangeEnd, int[] categories, int[] users,
                               String[] states);
}
