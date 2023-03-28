package ru.practicum.main.requests;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.requests.dto.DtoRequest;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class RequestController {
    private final RequestServiceImpl requestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{userId}/requests")
    public Request creatRequest(@PathVariable int userId, @RequestParam(value = "eventId") int eventId) {
        return requestService.creatRequest(userId, eventId);
    }

    @GetMapping(value = "/{userId}/requests")
    public List<Request> getRequestUser(@PathVariable int userId) {
        return requestService.getRequests(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public Request cancelRequest(@PathVariable(value = "userId") int userId, @PathVariable(value = "requestId") int requestId) {
        return requestService.cancelRequest(userId, requestId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests")
    public EvebtRequestUpdateStatusResult updateStatus(@PathVariable(value = "userId") int userId, @PathVariable(value = "eventId") int eventId, @RequestBody DtoRequest dtoRequest) {
        return requestService.updateStatus(userId, eventId, dtoRequest);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<Request> getRequestUserEvent(@PathVariable int userId, @PathVariable int eventId) {
        return requestService.getRequestUserEvent(userId, eventId);
    }
}
