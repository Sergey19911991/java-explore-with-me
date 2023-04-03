package ru.practicum.main.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.events.Event;
import ru.practicum.main.events.EventsRepository;
import ru.practicum.main.events.State;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.requests.dto.DtoRequest;
import ru.practicum.main.requests.dto.ParticipationReauestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    private final EventsRepository eventsRepository;

    private final MappingRequest mappingRequest;

    @Override
    public ParticipationReauestDto creatRequest(int userId, int eventId) {
        Event event = eventsRepository.findById(eventId).get();
        if (requestRepository.getRequestsEventConfirmed(eventId).size() >= event.getParticipantLimit()) {
            log.error("У события заполнен лимит участников!");
            throw new ConflictException("У события заполнен лимит участников!");
        }
        if (event.getInitiator().getId() == userId) {
            log.error("Добавление запроса от инициатора запроса!");
            throw new ConflictException("Добавление запроса от инициатора запроса!");
        }
        if (event.getState() == State.PENDING) {
            log.error("Добавление запроса на участие в неопубликованном событии!");
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
            log.info("Создан запрос");
            requestRepository.save(request);
            return mappingRequest.participationReauestDtoCancel(request);
        } else {
            log.error("Добавление повторного запроса от пользователя!");
            throw new ConflictException("Добавление повторного запроса от пользователя!");
        }
    }

    @Override
    public List<ParticipationReauestDto> getRequests(int userId) {
        log.info("Информация о запросах пользователя с id = {}", userId);
        List<Request> requests = requestRepository.getRequestsUser(userId);
        List<ParticipationReauestDto> participationReauestDtos = new ArrayList<>();
        for (Request request : requests) {
            participationReauestDtos.add(mappingRequest.participationReauestDtoCancel(request));
        }
        return participationReauestDtos;
    }

    @Override
    public ParticipationReauestDto cancelRequest(int userId, int requestId) {
        Request request = requestRepository.getRequestsUser(userId, requestId);
        request.setStatus(String.valueOf(State.CANCELED));
        log.info("Отменен запрос с id = {}", requestId);
        requestRepository.save(request);
        return mappingRequest.participationReauestDtoCancel(request);
    }

    @Override
    public EvebtRequestUpdateStatusResult updateStatus(int userId, int eventId, DtoRequest dtoRequest) {
        Event event = eventsRepository.findById(eventId).get();
        EvebtRequestUpdateStatusResult evebtRequestUpdateStatusResult = new EvebtRequestUpdateStatusResult();
        List<Request> requests = requestRepository.getRequests(dtoRequest.getRequestIds());
        if (dtoRequest.getStatus() == Status.CONFIRMED) {
            if (requestRepository.getRequestsEventConfirmed(eventId).size() >= event.getParticipantLimit()) {
                log.error("У события заполнен лимит участников!");
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
                    log.error("Отмена уже принятой заявки");
                    throw new ConflictException("Отмена уже принятой заявки");
                }
            }
            evebtRequestUpdateStatusResult.setRejectedRequests(requests);
        }
        log.info("Перезаписан запрос");
        return evebtRequestUpdateStatusResult;
    }

    @Override
    public List<ParticipationReauestDto> getRequestUserEvent(int userId, int eventId) {
        List<Request> requests = requestRepository.getRequestsUserEvent1(eventId);
        List<ParticipationReauestDto> participationReauestDtos = new ArrayList<>();
        for (Request request : requests) {
            participationReauestDtos.add(mappingRequest.participationReauestDtoCancel(request));
        }
        log.info("Информация о запросах");
        return participationReauestDtos;
    }
}
