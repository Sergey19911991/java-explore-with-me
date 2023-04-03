package ru.practicum.main.exception;

public class OtherException extends RuntimeException {
    public OtherException(String message) {
        super(message);
    }

    public OtherException(Throwable cause) {
        super(cause);
    }
}
