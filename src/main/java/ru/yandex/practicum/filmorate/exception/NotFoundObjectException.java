package ru.yandex.practicum.filmorate.exception;

public class NotFoundObjectException extends RuntimeException{
    public NotFoundObjectException(String message) {
        super(message);
    }
}
