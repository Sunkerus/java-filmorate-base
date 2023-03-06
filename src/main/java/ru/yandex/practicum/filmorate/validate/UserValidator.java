package ru.yandex.practicum.filmorate.validate;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;

public class UserValidator {

    public static void validateCorrect(User user) throws ValidationException {
        validateLogin(user);
        validateBirthday(user);
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    private static void validateLogin(User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Поле login не должно содержать пробельный символ");
        }
    }

    private static void validateBirthday(User user) throws ValidationException {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата дня рождения не должна быть указана в будущем.");
        }
    }
}
