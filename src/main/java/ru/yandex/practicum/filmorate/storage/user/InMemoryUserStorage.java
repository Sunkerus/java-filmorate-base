package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.instances.InternalServerException;
import ru.yandex.practicum.filmorate.exception.instances.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.instances.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users;
    private Integer userId = 0;

    public InMemoryUserStorage() {
        users = new HashMap<>();
    }


    @Override
    public Collection<User> getAll() {
        return users.values();
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
        boolean isUserExist = users.values().stream().anyMatch(u -> u.equals(user));
        if (isUserExist) {
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
    public User update(User user) throws InternalServerException {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new InternalServerException("Пользователь не найден");
        }
    }

    @Override
    public boolean containsUser(Integer id) throws NotFoundObjectException {
        if (users.containsKey(id)) {
            return true;
        } else {
            throw new NotFoundObjectException("Невозможно найти пользователя");
        }
    }
}
