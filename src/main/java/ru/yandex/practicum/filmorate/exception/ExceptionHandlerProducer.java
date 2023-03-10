package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerProducer {

    //400 — если ошибка валидации: ValidationException;
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return Map.of("error","Возникла ошибка валидации",
                "errorMessage", e.getMessage());
    }

    //404 — для всех ситуаций, если искомый объект не найден;
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundObjectException(NotFoundObjectException e) {
        log.error(e.getMessage(), e);
        return Map.of("error","Объект не найден",
                    "errorMessage", e.getMessage());
    }

    //500 — если возникло исключение.
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handle(Throwable e) {
        log.error(e.getMessage(), e);
        return Map.of("error","Возникло исключение",
                "errorMessage", e.getMessage());
    }





}
