package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.validate.Validator.filmValidation;

@Slf4j
@RestController
public class FilmController {

    private final HashMap<Integer, Film> filmStorage = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.trace("Был запрошен список фильмов");
        return filmStorage.values();
    }

    @PostMapping("/films")
    public Film postFilms(Film film) throws ValidationException {
            filmValidation(film);
            log.trace("Был добавлен фильм");
            filmStorage.put(film.getId(),film);
            return film;
    }

    @PutMapping("/films")
    public Film putFilms(Film film)  throws ValidationException {
        filmValidation(film);
        log.trace("Фильм был обновлен");
        filmStorage.put(film.getId(), film);
        return film;
        }


}

