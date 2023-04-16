package ru.practicum.main.requests;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.main.requests.dto.ParticipationReauestDto;

import java.util.List;

@Getter
@Setter
public class EvebtRequestUpdateStatusResult {
    private List<ParticipationReauestDto> confirmedRequests;
    private List<ParticipationReauestDto> rejectedRequests;
}
