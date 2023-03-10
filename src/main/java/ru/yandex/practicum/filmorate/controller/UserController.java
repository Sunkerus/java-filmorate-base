package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.instances.NotFoundObjectException;
import ru.yandex.practicum.filmorate.exception.instances.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriend(@NotNull @Positive @PathVariable Integer id) {
        return userService.getFriendsById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(name = "id") @NotNull @Positive Integer firstUserId,
                                       @PathVariable(name = "otherId") @NotNull @Positive Integer secondUserId) throws NotFoundObjectException {
        return userService.getMutualFriendsById(firstUserId, secondUserId);
    }

    @GetMapping("/{id}")
    public User getUserById(@NotNull @Positive @PathVariable Integer id) throws NotFoundObjectException {
        return userService.getUserById(id);
    }

    @PostMapping
    public User addUser(@NotNull @Valid @RequestBody User user) throws ValidationException {
        return userService.addUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public User addFriend(@NotNull @Positive @PathVariable Integer id,
                          @NotNull @Positive @PathVariable Integer friendId) throws Exception {
        return userService.addFriendById(id, friendId);
    }

    @PutMapping
    public User updateUser(@RequestBody @NotNull @Valid User user) throws Exception {
        return userService.updateUser(user);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public User deleteFriend(@NotNull @Positive @PathVariable Integer id,
                             @NotNull @Positive @PathVariable Integer friendId) throws Exception {
        return userService.deleteFriendById(id, friendId);
    }

    @DeleteMapping("/{id}")
    public User deleteUserById(@NotNull @Positive @PathVariable Integer id) throws NotFoundObjectException {
        return userService.deleteUserById(id);
    }
}
