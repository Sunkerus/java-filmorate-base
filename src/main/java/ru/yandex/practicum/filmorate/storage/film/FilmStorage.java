package ru.yandex.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getAll();
    Film get(Integer id) throws Exception;

    Film update(Film film) throws Exception;

    Film add(Film film) throws Exception;

    Film delete(Integer id) throws Exception;

}
