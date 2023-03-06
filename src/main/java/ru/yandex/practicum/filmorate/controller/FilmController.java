package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validate.FilmValidator.validate;

@Slf4j
@RestController
public class FilmController {

    private int filmIdent = 0;
    private final Map<Integer, Film> filmStorage = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.trace("Был запрошен список фильмов");
        return filmStorage.values();
    }

    @PostMapping("/films")
    public Film postFilm(@Valid @NotNull @RequestBody Film film) throws ValidationException {
        validate(film);
        film.setId(++filmIdent);
        log.trace("Был добавлен фильм c id: " + film.getId());
        filmStorage.put(film.getId(), film);
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @NotNull @RequestBody Film film) throws ValidationException {
        if (filmStorage.containsKey(film.getId())) {
            validate(film);
            log.trace("Фильм был обновлен");
            filmStorage.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Фильм с таким id не найден");
        }
    }


}

