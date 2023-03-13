package ru.yandex.practicum.filmorate.exception.instances;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
