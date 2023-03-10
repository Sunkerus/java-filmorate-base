package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getAll();
    Film get(Integer id) throws NotFoundObjectException;

    Film update(Film film);

    Film add(Film film);

    Film delete(Integer id) throws NotFoundObjectException;

}
