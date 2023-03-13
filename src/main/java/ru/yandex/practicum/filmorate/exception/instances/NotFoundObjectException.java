package ru.yandex.practicum.filmorate.exception.instances;

public class NotFoundObjectException extends RuntimeException{
    public NotFoundObjectException(String message) {
        super(message);
    }
}
