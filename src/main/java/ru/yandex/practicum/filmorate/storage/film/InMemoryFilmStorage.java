package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.instances.InternalServerException;
import ru.yandex.practicum.filmorate.exception.instances.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films;
    private int filmId = 0;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public Film get(Integer id) throws NotFoundObjectException {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundObjectException("Невозможно найти пользователя");
        }
    }

    @Override
    public Film add(Film film)  {
        film.setId(++filmId);
        films.put(film.getId(), film);
        return film;
    }


    @Override
    public Film delete(Integer id) throws NotFoundObjectException {
        Film deleteFilm = films.remove(id);
        if (deleteFilm != null) {
            return deleteFilm;
        } else {
            throw new NotFoundObjectException("Объект не найден");
        }
    }

    @Override
    public Film update(Film film) throws InternalServerException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new InternalServerException("Невозможно обновить");
        }
    }
}
