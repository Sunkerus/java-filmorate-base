package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.instances.InternalServerException;
import ru.yandex.practicum.filmorate.exception.instances.NotFoundObjectException;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validate.FilmValidator.validate;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Film addFilm(Film film)  {
        validate(film);
        Film newFilm = filmStorage.add(film);
        log.debug("Был добавлен новый фильм: {}, {}", newFilm.getId(), newFilm.getName());
        return newFilm;
    }

    public Collection<Film> getAll() {
        Collection<Film> users = filmStorage.getAll();
        log.debug("Были получены все фильмы");
        return users;
    }

    public Film getFilmById(Integer id)  {
        Film film = filmStorage.get(id);
        log.debug("Получен фильм с id: {}, {} и названием", id, film.getName());
        return film;
    }

    public List<Film> getPopularFilms(Integer count) {
        List<Film> films = filmStorage.getAll()
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getRate(), o1.getRate()))
                .limit(count)
                .collect(Collectors.toList());

        log.debug("Получены популярные фильмы");
        return films;
    }

    public Film updateFilm(Film film)  {
        validate(film);
        Film updateFilm = filmStorage.update(film);
        log.debug("Фильм с id {}, {}, был обновлен.", updateFilm.getName(), film.getId());
        return updateFilm;
    }

    public Film updateUserLikesFilms(Integer id, Integer userId) throws NotFoundObjectException, InternalServerException{
        Film film = filmStorage.get(id);
        if (userService.containsUser(userId)) {
            if (film.addUserLikes(userId)) {
                film.setRate(film.getRate() + 1);
                log.debug("Для пользователя с id {} были добавлены лайки {}", id, film.getUserLikes());
                return filmStorage.update(film);
            } else {
                log.debug("Не удалось добавить информацию о лайках");
                throw new InternalServerException("Не удалось добавить информацию о лайках.");
            }
        } else {
            log.debug("Пользователь с таким userItd " + userId + " не найден");
            throw new NotFoundObjectException("Пользователь с таким userItd " + userId + " не найден");
        }
    }

    public Film deleteFilmById(Integer id)  {
        Film deleteFilm = filmStorage.delete(id);
        log.debug("Фильм: {}, {}, был удален", deleteFilm.getId(), deleteFilm.getName());
        return deleteFilm;
    }

    public Film deleteUserLikesFilms(Integer id, Integer userId) throws NotFoundObjectException {
        Film film = filmStorage.get(id);
        if (userService.containsUser(userId)) {
            if (film.deleteUserLike(userId)) {
                film.setRate(film.getRate() - 1);
                log.debug("Для пользователя с id {} были удалены все лайки {}", userId, film.getUserLikes());
                return filmStorage.update(film);
            } else {
                log.debug("Не удалось удалить информацию о лайках");
                throw new InternalServerException("Не удалось удалить информацию о лайках");
            }
        } else {
            log.debug("Пользователь с таким userItd " + userId + " не найден");
            throw new NotFoundObjectException("Фильм с таким id не найден");
        }
    }

}