package ru.yandex.practicum.filmorate.validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validate.Validator.userValidation;


public class UserValidateTest {

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = User.builder()
                .id(1)
                .name("name")
                .login("login")
                .email("sunkerus@yandex.ru")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    public void shouldErrorThrows() {

        final User emailEmpty = user.toBuilder().email("").build();
        final User emailNull = user.toBuilder().email(null).build();
        final User emailSpace = user.toBuilder().email(" ").build();

        assertThrows(ValidationException.class, () -> userValidation(emailEmpty));
        assertThrows(ValidationException.class, () -> userValidation(emailSpace));
        assertThrows(ValidationException.class, () -> userValidation(emailNull));
    }

    @Test
    public void shouldErrorThrowsWhenBirthdayDateIsIncorrect() {
        final User birthdayNotCorrectFuture = user.toBuilder().birthday(LocalDate.of(2077, 1, 1)).build();
        final User birthdayNull = user.toBuilder().birthday(null).build();

        assertThrows(ValidationException.class, () -> userValidation(birthdayNotCorrectFuture));
        assertThrows(ValidationException.class, () -> userValidation(birthdayNull));
    }


    @Test
    public void shouldErrorThrowsWhenNameIdCorrect() {
        User nameWithNull = user.toBuilder().name(null).build();
        User nameEmpty = user.toBuilder().name("").build();
        User nameWithSpace = user.toBuilder().name(" ").build();

        userValidation(nameWithSpace);
        assertEquals(nameWithSpace.getLogin(), nameWithSpace.getName());

        userValidation(nameWithNull);
        assertEquals(nameWithNull.getLogin(), nameWithNull.getName());

        userValidation(nameEmpty);
        assertEquals(nameEmpty.getLogin(), nameEmpty.getName());
    }


    @Test
    public void shouldErrorThrowsWhenLoginIsIncorrect() {
        final User loginEmpty = user.toBuilder().login("").build();
        final User loginSpace = user.toBuilder().login("log in").build();
        final User loginAllSpace = user.toBuilder().login(" ").build();
        final User loginNull = user.toBuilder().login(null).build();


        assertThrows(ValidationException.class, () -> userValidation(loginNull));
        assertThrows(ValidationException.class, () -> userValidation(loginSpace));
        assertThrows(ValidationException.class, () -> userValidation(loginAllSpace));
        assertThrows(ValidationException.class, () -> userValidation(loginEmpty));
    }

    @Test
    public void shouldErrorThrowsWhenObjectIsNull() {
        assertThrows(ValidationException.class, () -> userValidation(null));
    }


}
