package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.categorie.CategorieRepository;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.RequestException;
import ru.practicum.main.location.Location;
import ru.practicum.main.location.LocationRepository;
import ru.practicum.main.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    private final EventsRepository eventsRepository;

    private final UserRepository userRepository;

    private final MappingEvent mappingEvent;

    private final CategorieRepository categorieRepository;

    private final LocationRepository locationRepository;

    @Override
    public NewEvent creatEvent(DtoEvent dtoEvent, int userId) {
        if (dtoEvent.getAnnotation() == null || dtoEvent.getTitle() == null) {
            throw new RequestException("Некорректное тело запроса!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dtoEvent.getEventDate(), formatter);
        if (localDateTime.isBefore(LocalDateTime.now())) {
            throw new ConflictException("Добавление события на неподходящую дату!");
        }
        Event event = mappingEvent.mapping(dtoEvent);
        event.setCreatedOn(LocalDateTime.now().toString());
        event.setInitiator(userRepository.findById(userId).get());
        event.setCategory(categorieRepository.findById(dtoEvent.getCategory()).get());
        Location location = locationRepository.save(dtoEvent.getLocation());
        event.setLocation(location);
        event.setViews(0);
        event.setState(State.PENDING);
        event.setConfirmedRequests(0);
        Event event1 = eventsRepository.save(event);
        return mappingEvent.mappingNewEvent(event1);
    }

    @Override
    public List<EventUser> getEventUser(int id, int from, int size) {
        List<Event> events = eventsRepository.getEventsUser(id, from, size);
        List<EventUser> eventUsers = new ArrayList<>();
        for (Event event : events) {
            eventUsers.add(mappingEvent.mappingEventUser(event));
        }
        for (Event event : events) {
            event.setViews(event.getViews() + 1);
            eventsRepository.save(event);
        }
        return eventUsers;
    }

    @Override
    public Event updateAdmin(UpdateAdmin updateAdmin, int id) {
        if (updateAdmin.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(updateAdmin.getEventDate(), formatter);
            if (localDateTime.isBefore(LocalDateTime.now())) {
                throw new ConflictException("Изменение даты события на уже наступившую");
            }
        }
        Event event = eventsRepository.findById(id).get();
        mappingEvent.mappingUpdateAdmin(updateAdmin, event);
        if (updateAdmin.getCategory() != null) {
            event.setCategory(categorieRepository.findById(updateAdmin.getCategory()).get());
        }
        if (updateAdmin.getStateAction() == StateAction.PUBLISH_EVENT) {
            if (event.getState() == State.PENDING) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now().toString());
            } else {
                if (event.getState() == State.CANCELED) {
                    throw new ConflictException("Публикация отмененного события!");
                }
                if (event.getState() == State.PUBLISHED) {
                    throw new ConflictException("Публикация опубликованного события!");
                }
            }
        }
        if (updateAdmin.getStateAction() == StateAction.REJECT_EVENT) {
            if (event.getState() == State.PENDING) {
                event.setState(State.CANCELED);
            } else if (event.getState() == State.PUBLISHED) {
                throw new ConflictException("Отмена опубликованного события!");
            }
        }
        return eventsRepository.save(event);
    }

    @Override
    public List<Event> getEvent(String text, int[] categories, String paid, String rangeStart, String rangeEnd,
                                String onlyAvailable, Sort sort, int from, int size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("1900-01-01 01:01:00", formatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse("2100-01-01 01:01:00", formatter);
        if (rangeStart != null) {
            localDateTime = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null) {
            localDateTime1 = LocalDateTime.parse(rangeEnd, formatter);
        }
        List<Event> events = eventsRepository.getEvent(from, size, localDateTime, localDateTime1, categories, Boolean.valueOf(paid), text);
        for (Event event : events) {
            event.setViews(event.getViews() + 1);
            eventsRepository.save(event);
        }
        return events;
    }

    @Override
    public Event updateUser(UpdateAdmin updateAdmin, int userId, int eventId) {
        if (updateAdmin.getEventDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(updateAdmin.getEventDate(), formatter);
            if (localDateTime.isBefore(LocalDateTime.now())) {
                throw new ConflictException("Изменение даты события на уже наступившую");
            }
        }
        Event event = eventsRepository.findById(eventId).get();
        if (event.getState() == State.PUBLISHED) {
            throw new ConflictException("Изменение опубликованного события!");
        }
        mappingEvent.mappingUpdateAdmin(updateAdmin, event);
        if (updateAdmin.getCategory() != null) {
            event.setCategory(categorieRepository.findById(updateAdmin.getCategory()).get());
        }
        if (updateAdmin.getStateAction() == StateAction.CANCEL_REVIEW) {
            event.setState(State.CANCELED);
        }
        if (updateAdmin.getStateAction() == StateAction.SEND_TO_REVIEW) {
            event.setState(State.PENDING);
        }
        return eventsRepository.save(event);
    }

    @Override
    public Event getEventById(int id) {
        return eventsRepository.getEventsById(id);
    }

    @Override
    public Event getUserEventById(int userId, int eventId) {
        return eventsRepository.getUserEvet(userId, eventId);
    }

    ;

    @Override
    public List<Event> getAdminEvents(int size, int from, String rangeStart, String rangeEnd, int[] categories, int[] users,
                                      String[] states) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("1900-01-01 01:01:00", formatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse("2100-01-01 01:01:00", formatter);
        if (rangeStart != null) {
            localDateTime = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null) {
            localDateTime1 = LocalDateTime.parse(rangeEnd, formatter);
        }
        List<Event> events = eventsRepository.getEventAdmin(from, size, localDateTime, localDateTime1, categories, users, states);
        return events;
    }

}
