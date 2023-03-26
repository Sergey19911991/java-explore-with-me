package ru.practicum.main.requests;

import ru.practicum.main.requests.dto.DtoRequest;

import java.util.List;

public interface RequestService {
    Request creatRequest(int userId, int eventId);

    List<Request> getRequests(int userId);

    Request cancelRequest(int userId, int requestId);

    EvebtRequestUpdateStatusResult updateStatus(int userId, int eventId, DtoRequest dtoRequest);

    List<Request> getRequestUserEvent(int userId, int eventId);
}
