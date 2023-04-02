package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.categorie.Categorie;
import ru.practicum.main.categorie.CategorieDto;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class MappingEvent {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event mapping(DtoEvent dtoEvent) {
        Event event = new Event();
        event.setAnnotation(dtoEvent.getAnnotation());
        event.setTitle(dtoEvent.getTitle());
        event.setParticipantLimit(dtoEvent.getParticipantLimit());
        event.setEventDate(LocalDateTime.parse(dtoEvent.getEventDate(), formatter));
        event.setPaid(dtoEvent.getPaid());
        event.setRequestModeration(dtoEvent.getRequestModeration());
        event.setDescription(dtoEvent.getDescription());
        return event;
    }

    public NewEvent mappingNewEvent(Event event) {
        NewEvent newEvent = new NewEvent();
        newEvent.setId(event.getId());
        newEvent.setEventDate(event.getEventDate().format(formatter));
        newEvent.setAnnotation(event.getAnnotation());
        newEvent.setDescription(event.getDescription());
        newEvent.setCategory(event.getCategory());
        UserShortDto userShortDto = new UserShortDto();
        User user = event.getInitiator();
        userShortDto.setId(user.getId());
        userShortDto.setName(user.getName());
        newEvent.setInitiator(userShortDto);
        newEvent.setPaid(event.getPaid());
        newEvent.setTitle(event.getTitle());
        newEvent.setRequestModeration(event.getRequestModeration());
        newEvent.setState(event.getState());
        newEvent.setLocation(event.getLocation());
        newEvent.setParticipantLimit(event.getParticipantLimit());
        newEvent.setCreatedOn(event.getCreatedOn());
        if (event.getPublishedOn() != null) {
            newEvent.setPublishedOn(event.getPublishedOn().format(formatter));
        }
        return newEvent;
    }

    public EventUser mappingEventUser(Event event) {
        EventUser eventUser = new EventUser();
        eventUser.setId(event.getId());
        eventUser.setEventDate(event.getEventDate().format(formatter));
        CategorieDto categorieDto = new CategorieDto();
        Categorie categorie = event.getCategory();
        categorieDto.setId(categorie.getId());
        categorieDto.setName(categorie.getName());
        eventUser.setCategory(categorieDto);
        eventUser.setAnnotation(event.getAnnotation());
        eventUser.setTitle(event.getTitle());
        eventUser.setPaid(event.getPaid());
        return eventUser;
    }

    public Event mappingUpdateAdmin(UpdateAdmin updateAdmin, Event event) {
        if (updateAdmin.getAnnotation() != null) {
            if (!updateAdmin.getAnnotation().isBlank()) {
                event.setAnnotation(updateAdmin.getAnnotation());
            }
        }
        if (updateAdmin.getTitle() != null) {
            if (!updateAdmin.getTitle().isBlank()) {
                event.setTitle(updateAdmin.getTitle());
            }
        }
        if (updateAdmin.getParticipantLimit() != null) {
            event.setParticipantLimit(updateAdmin.getParticipantLimit());
        }
        if (updateAdmin.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateAdmin.getEventDate(), formatter));
        }
        if (updateAdmin.getPaid() != null) {
            event.setPaid(updateAdmin.getPaid());
        }
        if (updateAdmin.getRequestModeration() != null) {
            event.setRequestModeration(updateAdmin.getRequestModeration());
        }
        if (updateAdmin.getDescription() != null) {
            if (!updateAdmin.getDescription().isBlank()) {
                event.setDescription(updateAdmin.getDescription());
            }
        }
        return event;
    }


    public EventsShortDto mappingEventsShortDto(Event event) {
        EventsShortDto eventsShortDto = new EventsShortDto();
        eventsShortDto.setAnnotation(event.getAnnotation());
        CategorieDto categorieDto = new CategorieDto();
        categorieDto.setId(event.getCategory().getId());
        categorieDto.setName(event.getCategory().getName());
        eventsShortDto.setCategory(categorieDto);
        eventsShortDto.setEventDate(event.getEventDate().format(formatter));
        eventsShortDto.setId(event.getId());
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(event.getInitiator().getId());
        userShortDto.setName(event.getInitiator().getName());
        eventsShortDto.setInitiator(userShortDto);
        eventsShortDto.setPaid(event.getPaid());
        eventsShortDto.setTitle(event.getTitle());
        return eventsShortDto;
    }

}
