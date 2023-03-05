package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.validate.Validator.userValidation;

@Slf4j
@RestController
public class UserController {

    private int userIdent = 0;
    private final HashMap<Integer, User> users = new HashMap<>();

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        log.trace("Была запрошена информация о всех пользователях");
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@Valid @NotNull @RequestBody User user) throws ValidationException {
        userValidation(user);
        user.setId(userIdent++);
        log.trace("Была добавлена информация о пользователе с id: " + user.getId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @NotNull @RequestBody User user) throws ValidationException {
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.put(user.getId(),user);
            return users.get(user.getId());
        } else {throw new ValidationException("Пользователь с таким id не найден. ");}
    }
}
