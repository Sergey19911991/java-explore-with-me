package ru.practicum.main.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.Event;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.events.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.requests.dto.DtoRequest;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    private final EventsRepository eventsRepository;

    @Override
    public Request creatRequest(int userId, int eventId) {
        Event event = eventsRepository.findById(eventId).get();
        if (requestRepository.getRequestsEventConfirmed(eventId).size() >= event.getParticipantLimit()) {
            throw new ConflictException("У события заполнен лимит участников!");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("Добавление запроса от инициатора запроса!");
        }
        if (event.getState() == State.PENDING) {
            throw new ConflictException("Добавление запроса на участие в неопубликованном событии!");
        }
        List<Request> requests = requestRepository.getRequestsUserEvent(userId, eventId);
        if (requests.size() == 0) {
            Request request = new Request();
            request.setRequester(userId);
            request.setEvent(eventId);
            if (event.getRequestModeration() == false) {
                request.setStatus(String.valueOf(State.CONFIRMED));
            }
            if (event.getRequestModeration() == true) {
                request.setStatus(String.valueOf(State.PENDING));
            }
            request.setCreated(LocalDateTime.now().toString());
            return requestRepository.save(request);
        } else {
            throw new ConflictException("Добавление повторного запроса от пользователя!");
        }
    }

    @Override
    public List<Request> getRequests(int userId) {
        return requestRepository.getRequestsUser(userId);
    }

    @Override
    public Request cancelRequest(int userId, int requestId) {
        Request request = requestRepository.getRequestsUser(userId, requestId);
        request.setStatus(String.valueOf(State.CANCELED));
        return requestRepository.save(request);
    }

    @Override
    public EvebtRequestUpdateStatusResult updateStatus(int userId, int eventId, DtoRequest dtoRequest) {
        Event event = eventsRepository.findById(eventId).get();
        EvebtRequestUpdateStatusResult evebtRequestUpdateStatusResult = new EvebtRequestUpdateStatusResult();
        List<Request> requests = requestRepository.getRequests(dtoRequest.getRequestIds());
        if (dtoRequest.getStatus() == Status.CONFIRMED) {
            if (requestRepository.getRequestsEventConfirmed(eventId).size() >= event.getParticipantLimit()) {
                throw new ConflictException("У события заполнен лимит участников!");
            }
            for (Request request : requests) {
                request.setStatus(String.valueOf(State.CONFIRMED));
                requestRepository.save(request);
            }
            evebtRequestUpdateStatusResult.setConfirmedRequests(requests);
        }
        if (dtoRequest.getStatus() == Status.REJECTED) {
            for (Request request : requests) {
                if (!request.getStatus().equals(String.valueOf(State.CONFIRMED))) {
                    request.setStatus(String.valueOf(State.REJECTED));
                    requestRepository.save(request);
                } else {
                    throw new ConflictException("Отмена уже принятой заявки");
                }
            }
            evebtRequestUpdateStatusResult.setRejectedRequests(requests);
        }
        return evebtRequestUpdateStatusResult;
    }

    @Override
    public List<Request> getRequestUserEvent(int userId, int eventId) {
        return requestRepository.getRequestsUserEvent1(eventId);
    }
}
