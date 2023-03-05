package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class User {

    private Integer id;

    @Email
    private String email;

    @NotEmpty(message = "Поле login не может быть пустым")
    private String login;

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;


}
