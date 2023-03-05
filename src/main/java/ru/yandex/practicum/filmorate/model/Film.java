package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
public class Film {

    @Positive(message = "id должно быть положительным")
    private int id;

    @NotEmpty(message = "Поле name не должно быть пустым.")
    private String name;

    @NotEmpty(message = "Поле description не должно быть пустым.")
    private String description;

    @NotEmpty(message = "Пооле не должно быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "releaseDate не может быть в будущем")
    private LocalDate releaseDate;

    @Positive(message = "Переменная duration должна быть положительной")
    @NotNull(message = "Необходимо добавить переменную duration")
    private int duration;
}