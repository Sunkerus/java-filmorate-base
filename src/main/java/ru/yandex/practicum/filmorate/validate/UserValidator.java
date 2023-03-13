package ru.yandex.practicum.filmorate.validate;

import ru.yandex.practicum.filmorate.exception.instances.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

public class UserValidator {

    public static void validateCorrect(User user) throws ValidationException {
        validateLogin(user);
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
    }

    private static void validateLogin(User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Поле login не должно содержать пробельный символ");
        }
    }
}
