package ru.practicum.main.exception;

public class ValidationExeption extends RuntimeException {
    public ValidationExeption(String message) {
        super(message);
    }

    public ValidationExeption(Throwable cause) {
        super(cause);
    }

}
