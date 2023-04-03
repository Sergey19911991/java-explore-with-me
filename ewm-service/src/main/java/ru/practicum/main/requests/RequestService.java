package ru.practicum.main.requests;

import ru.practicum.main.requests.dto.DtoRequest;
import ru.practicum.main.requests.dto.ParticipationReauestDto;

import java.util.List;

public interface RequestService {
    ParticipationReauestDto creatRequest(int userId, int eventId);

    List<ParticipationReauestDto> getRequests(int userId);

    ParticipationReauestDto cancelRequest(int userId, int requestId);

    EvebtRequestUpdateStatusResult updateStatus(int userId, int eventId, DtoRequest dtoRequest);

    List<ParticipationReauestDto> getRequestUserEvent(int userId, int eventId);
}
