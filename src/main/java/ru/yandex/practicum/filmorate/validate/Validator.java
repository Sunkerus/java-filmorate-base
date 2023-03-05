package ru.yandex.practicum.filmorate.validate;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    public static void filmValidation(Film film) throws ValidationException {
        if (film != null ) {
            if (!(film.getName() == null || film.getName().isBlank())) {
                if (!(film.getDescription() == null || film.getDescription().isBlank())) {
                    if (film.getDescription().length() <= 200) {
                        if (!(film.getReleaseDate().isBefore(LocalDate.of(1895, 1, 28)))) {
                            if ((film.getDuration() <= 0)) {
                                throw new ValidationException("Продолжительность фильма не может быть отрицательной");
                            }
                        } else {
                            throw new ValidationException("Дата не может быть указана раньше, чем 1895.01.28");
                        }
                    }else{
                        throw new ValidationException("Длина строки не может превышать 200");
                    }
                } else {
                    throw new ValidationException("Описание фильма не может соотвествовать данному значению");
                }
            } else {
                throw new ValidationException("Имя фильма не может быть пустым");
            }
        }else {throw new ValidationException("Фильм не может быть null");}
    }


    public static void userValidation(User user) throws ValidationException {
        if (user != null) {
            if (!(user.getEmail() == null || user.getEmail().isBlank())) {
                if (!(user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" "))) {
                    if (!(user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now()))) {
                        if (user.getName() == null || user.getName().isBlank()) {
                            user.setName(user.getLogin());
                        }
                    } else {
                        throw new ValidationException("Дата рождения не может быть в будущем!");
                    }
                } else {
                    throw new ValidationException("Логин пустой или содержит пробелы");
                }
            } else {
                throw new ValidationException("Некорректный Email");
            }
        } else {
            throw new ValidationException("user не должен быть null");
        }
    }
}