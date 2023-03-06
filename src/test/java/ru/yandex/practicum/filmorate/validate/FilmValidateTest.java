package ru.yandex.practicum.filmorate.validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validate.FilmValidator.validate;
public class FilmValidateTest {

    private Film film;

    @BeforeEach
    public void beforeEach() {
        film = Film.builder()
                .id(1)
                .name("Cimena")
                .description("some description for cinema")
                .duration(180)
                .releaseDate(LocalDate.of(2005, 1, 1))
                .build();
    }

    @Test
    void shouldErrorThrowsWhenReleaseDateIsEarlyThenStartDate() {
        final Film filmFailReleaseDate = film.toBuilder().releaseDate(LocalDate.of(1800, 1, 1)).build();

        assertThrows(ValidationException.class, () -> validate(filmFailReleaseDate));
    }

    @Test
    void shouldErrorThrowsWhenDescriptionIsBig() {
        final String bigDescription = "bigDescription".repeat(250);

        final Film filmFailReleaseDate = film.toBuilder().description(bigDescription).build();

        assertThrows(ValidationException.class, () -> validate(filmFailReleaseDate));
    }



}
