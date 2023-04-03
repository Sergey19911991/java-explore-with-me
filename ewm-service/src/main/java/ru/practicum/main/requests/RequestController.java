package ru.practicum.main.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.requests.dto.DtoRequest;
import ru.practicum.main.requests.dto.ParticipationReauestDto;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}/requests")
    public ParticipationReauestDto creatRequest(@PathVariable int userId, @RequestParam(value = "eventId") int eventId) {
        log.info("Создан запрос");
        return requestService.creatRequest(userId, eventId);
    }

    @GetMapping(value = "/{userId}/requests")
    public List<ParticipationReauestDto> getRequestUser(@PathVariable int userId) {
        log.info("Информация о запросах пользователя с id = {}",userId);
        return requestService.getRequests(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public ParticipationReauestDto cancelRequest(@PathVariable(value = "userId") int userId, @PathVariable(value = "requestId") int requestId) {
        log.info("Отменен запрос с id = {}",requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests")
    public EvebtRequestUpdateStatusResult updateStatus(@PathVariable(value = "userId") int userId, @PathVariable(value = "eventId") int eventId, @RequestBody DtoRequest dtoRequest) {
        log.info("Перезаписан запрос");
        return requestService.updateStatus(userId, eventId, dtoRequest);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<ParticipationReauestDto> getRequestUserEvent(@PathVariable int userId, @PathVariable int eventId) {
        log.info("Информация о запросах");
        return requestService.getRequestUserEvent(userId, eventId);
    }
}
