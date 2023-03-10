package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface UserStorage {
    User get(Integer id);

    User add(User user);

    User delete(Integer id);

    User update(User user) throws Exception;

    Collection<User> getAll();

    boolean containsUser(Integer id);
}