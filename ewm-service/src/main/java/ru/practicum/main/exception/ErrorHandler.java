package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final NotFoundException e) {
        return Map.of("error", "Ошибка",
                "errorMessage", e.getMessage(),
                "status", "404"
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleRequestException(final RequestException e) {
        return Map.of("error", "Ошибка",
                "errorMessage", e.getMessage(),
                "status", "400"
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(final ConflictException e) {
        return Map.of("error", "Ошибка",
                "errorMessage", e.getMessage()
        );
    }


}
