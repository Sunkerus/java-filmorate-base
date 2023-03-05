package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {

    @Positive(message = "Поле id должно быть положительным")
    private int id;

    @Email
    private String email;

    @NotEmpty(message = "Поле login не может быть пустым")
    private String login;

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate birthday;


}
