package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.categorie.CategorieRepository;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.exception.ConflictException;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public NewEvent creatEvent(DtoEvent dtoEvent, int userId) {
        LocalDateTime localDateTime = LocalDateTime.parse(dtoEvent.getEventDate(), formatter);
        if (localDateTime.isBefore(LocalDateTime.now())) {
            log.error("Добавление события на неподходящую дату!");
            throw new ConflictException("Добавление события на неподходящую дату!");
        }
        Event event = mappingEvent.mapping(dtoEvent);
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(userRepository.findById(userId).get());
        event.setCategory(categorieRepository.findById(dtoEvent.getCategory()).get());
        event.setLocation(dtoEvent.getLocation());
        event.setState(State.PENDING);
        Event event1 = eventsRepository.save(event);
        log.info("Создано событие");
        return mappingEvent.mappingNewEvent(event1);
    }

    @Override
    public List<EventUser> getEventUser(int id, int from, int size) {
        List<Event> events = eventsRepository.getEventsUser(id, from, size);
        List<EventUser> eventUsers = new ArrayList<>();
        for (Event event : events) {
            eventUsers.add(mappingEvent.mappingEventUser(event));
        }
        log.info("Информация о событиях");
        return eventUsers;
    }

    @Override
    public NewEvent updateAdmin(UpdateAdmin updateAdmin, int id) {
        if (updateAdmin.getEventDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(updateAdmin.getEventDate(), formatter);
            if (localDateTime.isBefore(LocalDateTime.now())) {
                log.error("Изменение даты события на уже наступившую");
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
                event.setPublishedOn(LocalDateTime.now());
            } else {
                if (event.getState() == State.CANCELED) {
                    log.error("Публикация отмененного события!");
                    throw new ConflictException("Публикация отмененного события!");
                }
                if (event.getState() == State.PUBLISHED) {
                    log.error("Публикация опубликованного события!");
                    throw new ConflictException("Публикация опубликованного события!");
                }
            }
        }
        if (updateAdmin.getStateAction() == StateAction.REJECT_EVENT) {
            if (event.getState() == State.PENDING) {
                event.setState(State.CANCELED);
            } else if (event.getState() == State.PUBLISHED) {
                log.error("Отмена опубликованного события!");
                throw new ConflictException("Отмена опубликованного события!");
            }
        }
        eventsRepository.save(event);
        log.info("Перезаписано событие с id = {}",id);
        return mappingEvent.mappingNewEvent(event);
    }

    @Override
    public List<EventsShortDto> getEvent(String text, int[] categories, String paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         String onlyAvailable, Sort sort, int from, int size) {

        List<Event> events = eventsRepository.getEvent(from, size, rangeStart, rangeEnd, categories, Boolean.valueOf(paid), text);
        List<EventsShortDto> eventsShortDtos = new ArrayList<>();
        for (Event event : events) {
            eventsShortDtos.add(mappingEvent.mappingEventsShortDto(event));
        }
        log.info("Информация о событиях");
        return eventsShortDtos;
    }

    @Override
    public NewEvent updateUser(UpdateAdmin updateAdmin, int userId, int eventId) {
        if (updateAdmin.getEventDate() != null) {
            LocalDateTime localDateTime = LocalDateTime.parse(updateAdmin.getEventDate(), formatter);
            if (localDateTime.isBefore(LocalDateTime.now())) {
                log.error("Изменение даты события на уже наступившую");
                throw new ConflictException("Изменение даты события на уже наступившую");
            }
        }
        Event event = eventsRepository.findById(eventId).get();
        if (event.getState() == State.PUBLISHED) {
            log.error("Изменение опубликованного события!");
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
        eventsRepository.save(event);
        log.info("Перезаписано событие");
        return mappingEvent.mappingNewEvent(event);
    }

    @Override
    public NewEvent getEventById(int id) {
        NewEvent newEvent = mappingEvent.mappingNewEvent(eventsRepository.getEventsById(id));
        log.info("Информация о событии с id = {}",id);
        return newEvent;
    }

    @Override
    public Event getUserEventById(int userId, int eventId) {
        log.info("Информация о событии с id = {}",eventId);
        return eventsRepository.getUserEvet(userId, eventId);
    }


    @Override
    public List<NewEvent> getAdminEvents(int size, int from, LocalDateTime rangeStart, LocalDateTime rangeEnd, int[] categories, int[] users,
                                         String[] states) {
        List<Event> events = eventsRepository.getEventAdmin(from, size, rangeStart, rangeEnd, categories, users, states);
        List<NewEvent> newEvents = new ArrayList<>();
        for (Event event : events) {
            NewEvent newEvent = mappingEvent.mappingNewEvent(event);
            newEvents.add(newEvent);
        }
        log.info("Информация о событиях");
        return newEvents;
    }
}
