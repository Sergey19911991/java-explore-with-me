package ru.practicum.main.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.main.requests.dto.ParticipationReauestDto;

@RequiredArgsConstructor
@Service
public class MappingRequest {
    public ParticipationReauestDto participationReauestDtoCancel(Request request) {
        ParticipationReauestDto participationReauestDto = new ParticipationReauestDto();
        participationReauestDto.setId(request.getId());
        participationReauestDto.setRequester(request.getRequester());
        participationReauestDto.setCreated(request.getCreated());
        participationReauestDto.setEvent(request.getEvent());
        participationReauestDto.setStatus(request.getStatus());
        return participationReauestDto;
    }
}
