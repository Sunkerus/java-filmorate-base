package ru.yandex.practicum.filmorate.validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static ru.yandex.practicum.filmorate.validate.UserValidator.validateCorrect;


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
    public void shouldErrorThrowsWhenNameIdCorrect() {
        User nameWithNull = user.toBuilder().name(null).build();
        User nameEmpty = user.toBuilder().name("").build();
        User nameWithSpace = user.toBuilder().name(" ").build();

        validateCorrect(nameWithSpace);
        assertEquals(nameWithSpace.getLogin(), nameWithSpace.getName());

        validateCorrect(nameWithNull);
        assertEquals(nameWithNull.getLogin(), nameWithNull.getName());

        validateCorrect(nameEmpty);
        assertEquals(nameEmpty.getLogin(), nameEmpty.getName());
    }


    @Test
    public void shouldErrorThrowsWhenLoginIsIncorrect() {
        final User loginSpace = user.toBuilder().login("log in").build();

        assertThrows(ValidationException.class, () -> validateCorrect(loginSpace));

    }

}
