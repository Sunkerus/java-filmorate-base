package ru.yandex.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface UserStorage {
    User get(Integer id)throws Exception;

    User add(User user)throws Exception;

    User delete(Integer id) throws Exception;

    User update(User user)throws Exception;

    Collection<User> getAll();

    boolean containsUser(Integer id) throws Exception;
}