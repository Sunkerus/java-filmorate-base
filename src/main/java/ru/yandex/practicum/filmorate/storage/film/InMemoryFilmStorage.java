package ru.yandex.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final Map<Integer, Film> films;
    private int filmId = 0;


    public InMemoryFilmStorage() {
        films = new HashMap<>();
    }

    @Override
    public Film get(Integer id) throws Exception {
        if(films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new Exception();
        }
    }

    @Override
    public Film add(Film film) throws Exception {
        boolean filmExist = films.values().stream().anyMatch(f -> f.equals(film));

        if(filmExist) {
            throw new Exception();
        }
        film.setId(++filmId);
        films.put(film.getId(),film);
        return film;
    }


    @Override
    public Film delete(Integer id) throws Exception {
        if(films.containsKey(id)) {
            Film deleteFilm = films.get(id);
            films.remove(deleteFilm.getId());
            return deleteFilm;
        } else {
            throw new Exception();
        }
    }

    @Override
    public Film update(Film film) throws Exception {
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new Exception();
        }
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

}
