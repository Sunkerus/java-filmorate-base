package ru.yandex.filmorate.storage.user;

import org.springframework.stereotype.Component;
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
    public User get(Integer id) throws Exception {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new Exception();
        }
    }

    @Override
    public User add(User user) throws Exception{
        boolean userExist = users.values().stream().anyMatch(u -> u.equals(user));

        if (userExist) {
            throw new Exception();
        }

        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(Integer id) throws Exception {
        if(users.containsKey(id)) {
            User deleteUser = users.get(id);
            users.remove(id);
            return deleteUser;
        } else {
            throw new Exception();
        }
    }

    @Override
    public User update(User user) throws Exception {
        if(users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return user;
        } else {
            throw new Exception();
        }
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public boolean containsUser(Integer id) throws Exception {
        if(users.containsKey(id)) return true;
        else throw new Exception();
    }
}
