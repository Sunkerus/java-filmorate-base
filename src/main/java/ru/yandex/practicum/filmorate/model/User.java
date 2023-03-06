package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {

    private Integer id;

    @NotBlank(message = "Поле email не должно быть пустым.")
    @Email
    private String email;

    @NotBlank(message = "Поле login не может быть пустым")
    private String login;

    private String name;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Поле birthday не может быть пустым")
    private LocalDate birthday;


}
