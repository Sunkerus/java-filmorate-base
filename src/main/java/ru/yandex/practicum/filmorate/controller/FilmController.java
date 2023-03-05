package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.validate.Validator.filmValidation;

@Slf4j
@RestController
public class FilmController {

    private int filmIdent = 0;
    private final HashMap<Integer, Film> filmStorage = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        log.trace("Был запрошен список фильмов");
        return filmStorage.values();
    }

    @PostMapping("/films")
    public Film postFilms(@Valid @NotNull @RequestBody Film film) throws ValidationException {
            filmValidation(film);
            film.setId(filmIdent++);
            log.trace("Был добавлен фильм c id: " + film.getId());
            filmStorage.put(film.getId(),film);
            return film;
    }

    @PutMapping("/films")
    public Film putFilms(@Valid @NotNull @RequestBody Film film)  throws ValidationException {
        filmValidation(film);
        log.trace("Фильм был обновлен");
        filmStorage.put(film.getId(), film);
        return film;
        }


}

