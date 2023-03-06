package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
public class Film {

    private Integer id;

    @NotBlank(message = "Поле name не должно быть пустым")
    private String name;

    @NotBlank(message = "Поле description не должно быть пустым.")
    private String description;

    @NotNull(message = "Поле releaseDate не должно быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull(message = "Поле duration не должно быть пустым")
    @Positive(message = "Переменная duration должна быть положительной")
    private int duration;
}