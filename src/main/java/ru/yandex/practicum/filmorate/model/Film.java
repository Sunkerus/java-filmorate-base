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

    private int id;

    @NotEmpty(message = "Поле name не должно быть пустым.")
    private String name;

    @NotEmpty(message = "Поле description не должно быть пустым.")
    private String description;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive(message = "Переменная duration должна быть положительной")
    @NotNull(message = "Необходимо добавить переменную duration")
    private int duration;
}