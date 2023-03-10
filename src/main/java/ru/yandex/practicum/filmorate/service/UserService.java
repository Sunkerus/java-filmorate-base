package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.yandex.practicum.filmorate.exception.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.validate.UserValidator.validateCorrect;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) throws ValidationException {
            validateCorrect(user);
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
            userStorage.add(user);
            log.debug("Пользователь: {} {} добавлен в хранилище", user.getId(), user.getName());
            return user;
        }
        User newUser = userStorage.add(user);
        log.debug("Был добавлен новый пользователь {}, {}", newUser.getId(), user.getName());
        return newUser;
    }

    public User updateUser(User user) throws ValidationException {
        validateCorrect(user);
        User updateUser = userStorage.update(user);
        log.debug("Пользователь {}, {}, удален", updateUser.getId(), user.getName());
        return updateUser;
    }
    public User deleteUserById(Integer id) throws NotFoundObjectException {
        User deleteUser = userStorage.delete(id);
        log.debug("Пользователь {}, {}, удален", deleteUser.getId(), deleteUser.getName());
        return deleteUser;
    }

    public Collection<User> getAll() {
        Collection<User> users = userStorage.getAll();
        log.debug("Были получены все пользователи");
        return users;
    }

    public User getUserById(Integer id) throws NotFoundObjectException{
        User user = userStorage.get(id);
        log.debug("Был получен пользователь {}, {}", id, user.getName());
        return user;
    }

    public User addFriendById(Integer id, Integer friendId) throws NotFoundObjectException {
        User user = userStorage.get(id);
        User friend = userStorage.get(friendId);

        if (user.addFriend(friendId) && friend.addFriend(id)) {
            userStorage.update(user);
            userStorage.update(friend);
            log.debug("Были добавлены новые друзья {} {} {}",
                    friend.getId(), friend.getName(), friend.getFriends());
            return user;
        } else {
            throw new NotFoundObjectException("Один или два друга не найдены");
        }
    }

    public User deleteFriendById(Integer firstFriend, Integer secondFriend) throws NotFoundObjectException {
        User user = userStorage.get(firstFriend);
        User friend = userStorage.get(secondFriend);

        if (user.deleteFriend(secondFriend) && friend.deleteFriend(firstFriend)) {
            userStorage.update(user);
            userStorage.update(friend);
            log.debug("Друг {} {}, был добавлен {}", secondFriend, friend.getName(),
                    friend.getFriends());
            return user;
        } else {
            throw new NotFoundObjectException("Объекты не найдены");
        }
    }

    public List<User> getFriendsById(Integer id) throws NotFoundObjectException {
        User user = userStorage.get(id);
        List<User> friends = new ArrayList<>();
        for (Integer friendId : user.getFriends()) {
            friends.add(userStorage.get(friendId));
        }
        log.debug("Была получена информация о друзьях");
        return friends;
    }

    public List<User> getMutalFriendsById(Integer user1Id, Integer user2Id) throws NotFoundObjectException {
        Set<Integer> firstFriendId = userStorage.get(user1Id).getFriends();
        Set<Integer> secondFriendId = userStorage.get(user2Id).getFriends();

        if (firstFriendId == null || secondFriendId.isEmpty()) {
            log.debug("У пользователя с id {} и id {} нет общих друзей", user1Id, user2Id);
            return new ArrayList<>();
        }

        List<Integer> commonFriendId = new ArrayList<>();
        for (Integer f1 : firstFriendId) {
            for (Integer f2 : secondFriendId) {
                if (f1.equals(f2)) {
                    commonFriendId.add(f1);
                }
            }
        }

        List<User> mutalFriends = new ArrayList<>();
        for (Integer friendId : commonFriendId) {
            mutalFriends.add(userStorage.get(friendId));
        }
        log.debug("Получены общие друзья у {} и {}", user1Id, user2Id);
        return mutalFriends;
    }

    public boolean containsUser(Integer id) throws NotFoundObjectException {
        return userStorage.containsUser(id);
    }


}