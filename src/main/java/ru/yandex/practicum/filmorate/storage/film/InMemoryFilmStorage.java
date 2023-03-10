package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Integer, Film> films;
    private int filmId = 0;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public Film get(Integer id) throws NotFoundObjectException {
        if(films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundObjectException("Невозможно найти пользователя");
        }
    }

    @Override
    public Film add(Film film) throws NotFoundObjectException {
        boolean filmExist = films.values().stream().anyMatch(f -> f.equals(film));

        if(filmExist) {
            throw new NotFoundObjectException("Фильм с таким id не существует");
        }
        film.setId(++filmId);
        films.put(film.getId(),film);
        return film;
    }


    @Override
    public Film delete(Integer id) throws NotFoundObjectException {
        if(films.containsKey(id)) {
            Film deleteFilm = films.get(id);
            films.remove(deleteFilm.getId());
            return deleteFilm;
        } else {
            throw new NotFoundObjectException("Объект не найден");
        }
    }

    @Override
    public Film update(Film film) throws NotFoundObjectException {
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new NotFoundObjectException("Невозможно обновить");
        }
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

}
