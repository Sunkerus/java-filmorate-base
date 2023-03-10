package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User get(Integer id);

    User add(User user);

    User delete(Integer id);

    User update(User user);

    Collection<User> getAll();

    boolean containsUser(Integer id);
}