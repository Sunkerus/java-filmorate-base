package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserStorage implements UserStorage {

    @Override
    public Collection<User> getAll() {
        return users.values();
    }
    private final Map<Integer, User> users;
    private Integer userId = 0;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }

    @Override
    public User get(Integer id) throws NotFoundObjectException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundObjectException("Невозможно получить информацию о пользователе");
        }
    }

    @Override
    public User add(User user) throws ValidationException {
        boolean userExist = users.values().stream().anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new ValidationException("Возникла проблема при добалвении пользователя");
        }

        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(Integer id) throws NotFoundObjectException {
        if (users.containsKey(id)) {
            User deleteUser = users.get(id);
            users.remove(id);
            return deleteUser;
        } else {
            throw new NotFoundObjectException("Пользователь не найден по id");
        }
    }

    @Override
    public User update(User user) throws NotFoundObjectException {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new NotFoundObjectException("Пользователь не найден");
        }
    }

    @Override
    public boolean containsUser(Integer id) throws NotFoundObjectException {
        if (users.containsKey(id)) return true;
        else throw new NotFoundObjectException("Невозможно найти пользователя");
    }
}
