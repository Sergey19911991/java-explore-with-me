package ru.practicum.main.events;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import hit.HitClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitDto;
import ru.practicum.main.categorie.CategorieRepository;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventsShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.requests.RequestRepository;
import ru.practicum.main.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final RequestRepository requestRepository;
    private final EventsRepository eventsRepository;

    private final UserRepository userRepository;

    private final MappingEvent mappingEvent;

    private final CategorieRepository categorieRepository;

    private final HitClient hitClient;

    private final LocalDateTime max = LocalDateTime.of(3023, 9, 19, 14, 5);

    private final LocalDateTime min = LocalDateTime.of(1023, 9, 19, 14, 5);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto creatEvent(NewEventDto dtoEvent, int userId) {
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
    public List<EventsShortDto> getEventUser(int id, int from, int size) {
        List<Event> events = eventsRepository.getEventsUser(id, from, size);
        List<EventsShortDto> eventUsers = new ArrayList<>();
        String[] uris = new String[100];
        int k = 0;
        for (Event event : events) {
            EventsShortDto eventsShortDto = mappingEvent.mappingEventUser(event);
            uris[k] = "/events/" + Integer.toString(event.getId());
            k = k + 1;
            eventUsers.add(eventsShortDto);
        }
        List<HitDto> hitDtoList = viewsStatsUris(uris);
        for (EventsShortDto eventsShortDto : eventUsers) {
            for (HitDto hitDto : hitDtoList) {
                if (hitDto.getUri().contains(String.valueOf(eventsShortDto.getId()))) {
                    eventsShortDto.setViews(hitDto.getHits() + eventsShortDto.getViews());
                }
            }
        }
        log.info("Информация о событиях");
        return eventUsers;
    }

    @Override
    public EventFullDto updateAdmin(UpdateEventAdminRequest updateAdmin, int id) {
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
        log.info("Перезаписано событие с id = {}", id);
        EventFullDto eventFullDto = mappingEvent.mappingNewEvent(event);
        List<HitDto> hitDtoList = viewsStats(id);
        for (HitDto hitDto : hitDtoList) {
            eventFullDto.setViews(hitDto.getHits() + eventFullDto.getViews());
        }
        return eventFullDto;
    }


    @Override
    public List<EventsShortDto> getEvent(String text, int[] categories, String paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         String onlyAvailable, Sort sort, int from, int size) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        if (!text.equals(null)) {
            conditions.add(event.description.contains(text).or(event.annotation.contains(text)));
        }

        if (!paid.equals(null)) {
            conditions.add(event.paid.eq(Boolean.valueOf(paid)));
        }

        if (rangeStart != null) {
            conditions.add(event.eventDate.after(rangeStart));
        }

        if (rangeEnd != null) {
            conditions.add(event.eventDate.before(rangeEnd));
        }


        if (categories != null) {
            List<Integer> categoryList = new ArrayList<>();
            for (int i = 0; i < categories.length; i++) {
                categoryList.add(categories[i]);
            }
            conditions.add(event.category.id.in(categoryList));
        }

        PageRequest pageRequest = PageRequest.of(from, size);

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Iterable<Event> events = eventsRepository.findAll(finalCondition, pageRequest);

        List<EventsShortDto> eventsShortDtos = new ArrayList<>();
        String[] uris = new String[100];
        int k = 0;
        for (Event event1 : events) {
            EventsShortDto eventsShortDto = mappingEvent.mappingEventsShortDto(event1);
            uris[k] = "/events/" + Integer.toString(event1.getId());
            k = k + 1;
            eventsShortDto.setConfirmedRequests(requestRepository.getRequestsEventConfirmed(event1.getId()).size());
            if (Boolean.valueOf(onlyAvailable) && (eventsShortDto.getConfirmedRequests() <= event1.getParticipantLimit() || event1.getParticipantLimit() == 0)) {
                eventsShortDtos.add(eventsShortDto);
            } else if (!Boolean.valueOf(onlyAvailable)) {
                eventsShortDtos.add(eventsShortDto);
            }
        }
        List<HitDto> hitDtoList = viewsStatsUris(uris);
        for (EventsShortDto eventsShortDto : eventsShortDtos) {
            for (HitDto hitDto : hitDtoList) {
                if (hitDto.getUri().contains(String.valueOf(eventsShortDto.getId()))) {
                    eventsShortDto.setViews(hitDto.getHits() + eventsShortDto.getViews());
                }
            }
        }
        if (sort == Sort.EVENT_DATE) {
            Collections.sort(eventsShortDtos, COMPARE_BY_EVENT_DATE);
        } else {
            Collections.sort(eventsShortDtos, COMPARE_BY_VIEWS);
        }
        log.info("Информация о событиях");
        return eventsShortDtos;
    }

    @Override
    public EventFullDto updateUser(UpdateEventAdminRequest updateAdmin, int userId, int eventId) {
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
        EventFullDto eventFullDto = mappingEvent.mappingNewEvent(event);
        List<HitDto> hitDtoList = viewsStats(event.getId());
        for (HitDto hitDto : hitDtoList) {
            eventFullDto.setViews(hitDto.getHits() + eventFullDto.getViews());
        }
        return eventFullDto;
    }

    @Override
    public EventFullDto getEventById(int id) {
        Event event = eventsRepository.getEventsById(id);
        if (event.getState() == State.PUBLISHED) {
            EventFullDto newEvent = mappingEvent.mappingNewEvent(event);
            List<HitDto> hitDtoList = viewsStats(id);
            for (HitDto hitDto : hitDtoList) {
                newEvent.setViews(hitDto.getHits() + newEvent.getViews());
            }
            newEvent.setConfirmedRequests(requestRepository.getRequestsEventConfirmed(id).size());
            log.info("Информация о событии с id = {}", id);
            return newEvent;
        } else {
            log.error("Событие не опубликовано!");
            throw new ConflictException("Событие не опубликовано!");
        }
    }

    @Override
    public EventFullDto getUserEventById(int userId, int eventId) {
        EventFullDto eventFullDto = mappingEvent.mappingNewEvent(eventsRepository.getUserEvet(userId, eventId));
        List<HitDto> hitDtoList = viewsStats(eventId);
        for (HitDto hitDto : hitDtoList) {
            eventFullDto.setViews(hitDto.getHits() + eventFullDto.getViews());
        }
        log.info("Информация о событии с id = {}", eventId);
        return eventFullDto;
    }


    @Override
    public List<EventFullDto> getAdminEvents(int size, int from, LocalDateTime rangeStart, LocalDateTime rangeEnd, int[] categories, int[] users,
                                             String[] states) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();

        if (rangeStart != null) {
            conditions.add(event.eventDate.after(rangeStart));
        }

        if (rangeEnd != null) {
            conditions.add(event.eventDate.before(rangeEnd));
        }

        if (categories != null) {
            List<Integer> categoryList = new ArrayList<>();
            for (int i = 0; i < categories.length; i++) {
                categoryList.add(categories[i]);
            }
            conditions.add(event.category.id.in(categoryList));
        }


        if (states != null) {
            List<State> stateList = new ArrayList<>();
            for (int i = 0; i < states.length; i++) {
                stateList.add(State.valueOf(states[i]));
            }
            conditions.add(event.state.in(stateList));
        }


        if (users != null) {
            List<Integer> usersList = new ArrayList<>();
            for (int i = 0; i < users.length; i++) {
                usersList.add(users[i]);
            }
            conditions.add(event.initiator.id.in(usersList));
        }

        PageRequest pageRequest = PageRequest.of(from, size);

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Iterable<Event> events = eventsRepository.findAll(finalCondition, pageRequest);

        List<EventFullDto> newEvents = new ArrayList<>();
        String[] uris = new String[100];
        int k = 0;
        for (Event event1 : events) {
            EventFullDto newEvent = mappingEvent.mappingNewEvent(event1);
            uris[k] = "/events/" + Integer.toString(event1.getId());
            k = k + 1;
            newEvent.setConfirmedRequests(requestRepository.getRequestsEventConfirmed(event1.getId()).size());
            newEvents.add(newEvent);
        }
        List<HitDto> hitDtoList = viewsStatsUris(uris);
        for (EventFullDto eventFullDto : newEvents) {
            for (HitDto hitDto : hitDtoList) {
                if (hitDto.getUri().contains(String.valueOf(eventFullDto.getId()))) {
                    eventFullDto.setViews(hitDto.getHits() + eventFullDto.getViews());
                }
            }
        }
        log.info("Информация о событиях");
        return newEvents;
    }

    private List<HitDto> viewsStats(int id) {
        String[] uris = new String[1];
        uris[0] = "/events/" + Integer.toString(id);
        ResponseEntity<Object> hits = hitClient.getHits(min.format(formatter), max.format(formatter), uris, false);
        List<HitDto> hitList = new ObjectMapper().convertValue(hits.getBody(), new TypeReference<List<HitDto>>() {
        });
        return hitList;
    }

    private List<HitDto> viewsStatsUris(String[] uris) {
        ResponseEntity<Object> hits = hitClient.getHits(min.format(formatter), max.format(formatter), uris, false);
        List<HitDto> hitList = new ObjectMapper().convertValue(hits.getBody(), new TypeReference<List<HitDto>>() {
        });
        return hitList;
    }

    public static final Comparator<EventsShortDto> COMPARE_BY_EVENT_DATE = new Comparator<EventsShortDto>() {
        @Override
        public int compare(EventsShortDto lhs, EventsShortDto rhs) {
            return lhs.getEventDate().compareTo(rhs.getEventDate());
        }
    };

    public static final Comparator<EventsShortDto> COMPARE_BY_VIEWS = new Comparator<EventsShortDto>() {
        @Override
        public int compare(EventsShortDto lhs, EventsShortDto rhs) {
            return lhs.getViews().compareTo(rhs.getViews());
        }
    };


}
