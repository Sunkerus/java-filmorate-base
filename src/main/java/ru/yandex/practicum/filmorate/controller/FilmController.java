package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.instances.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.instances.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable @NotNull Integer id) throws NotFoundObjectException {
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(
            @RequestParam(name = "count", required = false, defaultValue = "10")
            @NotNull @Positive Integer count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping
    public Film postFilm(@NotNull @Valid @RequestBody Film film) throws ValidationException {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@NotNull @Valid @RequestBody Film film) throws ValidationException {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film putlikesFilm(@NotNull @Positive @PathVariable Integer id,
                             @NotNull @Positive @PathVariable Integer userId) throws NotFoundObjectException {
        return filmService.updateUserFilmLikes(id, userId);
    }

    @DeleteMapping("/{id}")
    public Film deleteFilmById(@NotNull @Positive @PathVariable Integer id) throws NotFoundObjectException {
        return filmService.deleteFilmById(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLikeFilm(@NotNull @Positive @PathVariable Integer id,
                               @NotNull @Positive @PathVariable Integer userId) throws NotFoundObjectException {
        return filmService.deleteLikesForUser(id, userId);
    }
}

