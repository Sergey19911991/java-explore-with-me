package ru.practicum.main.events;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.dto.DtoEvent;
import ru.practicum.main.events.dto.EventUser;
import ru.practicum.main.events.dto.NewEvent;
import ru.practicum.main.events.dto.UpdateAdmin;

@RequiredArgsConstructor
@Service
public class MappingEvent {

    public Event mapping(DtoEvent dtoEvent) {
        Event event = new Event();
        event.setAnnotation(dtoEvent.getAnnotation());
        event.setTitle(dtoEvent.getTitle());
        event.setParticipantLimit(dtoEvent.getParticipantLimit());
        event.setEventDate(dtoEvent.getEventDate());
        event.setPaid(dtoEvent.getPaid());
        event.setRequestModeration(dtoEvent.getRequestModeration());
        event.setDescription(dtoEvent.getDescription());
        return event;
    }

    public NewEvent mappingNewEvent(Event event) {
        NewEvent newEvent = new NewEvent();
        newEvent.setId(event.getId());
        newEvent.setEventDate(event.getEventDate());
        newEvent.setAnnotation(event.getAnnotation());
        newEvent.setDescription(event.getDescription());
        newEvent.setCategory(event.getCategory());
        newEvent.setInitiator(event.getInitiator());
        newEvent.setPaid(event.getPaid());
        newEvent.setTitle(event.getTitle());
        newEvent.setRequestModeration(event.getRequestModeration());
        newEvent.setState(event.getState());
        newEvent.setLocation(event.getLocation());
        newEvent.setParticipantLimit(event.getParticipantLimit());
        newEvent.setCreatedOn(event.getCreatedOn());
        return newEvent;
    }

    public EventUser mappingEventUser(Event event) {
        EventUser eventUser = new EventUser();
        eventUser.setId(event.getId());
        eventUser.setEventDate(event.getEventDate());
        eventUser.setCategory(event.getCategory());
        eventUser.setAnnotation(event.getAnnotation());
        eventUser.setTitle(event.getTitle());
        eventUser.setPaid(event.getPaid());
        return eventUser;
    }

    public Event mappingUpdateAdmin(UpdateAdmin updateAdmin, Event event) {
        if (updateAdmin.getAnnotation() != null) {
            event.setAnnotation(updateAdmin.getAnnotation());
        }
        if (updateAdmin.getTitle() != null) {
            event.setTitle(updateAdmin.getTitle());
        }
        if (updateAdmin.getParticipantLimit() != null) {
            event.setParticipantLimit(updateAdmin.getParticipantLimit());
        }
        if (updateAdmin.getEventDate() != null) {
            event.setEventDate(updateAdmin.getEventDate());
        }
        if (updateAdmin.getPaid() != null) {
            event.setPaid(updateAdmin.getPaid());
        }
        if (updateAdmin.getRequestModeration() != null) {
            event.setRequestModeration(updateAdmin.getRequestModeration());
        }
        if (updateAdmin.getDescription() != null) {
            event.setDescription(updateAdmin.getDescription());
        }
        return event;
    }

}
