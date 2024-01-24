package com.example.news.dto.user;

import com.example.news.model.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя должно быть")
    @Size(min = 3, max = 30, message = "Имя должно быть от {min} до {max} символов")
    private String firstName;

    @NotBlank(message = "Фамилия должна быть")
    @Size(min = 3, max = 30, message = "Фамилия должна быть от {min} до {max} символов")
    private String secondName;

    @NotBlank(message = "Логин должен быть")
    @Size(min = 3, max = 30, message = "Логин должен быть от {min} до {max} символов")
    private String login;

    @NotBlank(message = "Пароль должен быть")
    @Size(min = 3, max = 30, message = "Логин должен быть от {min} до {max} символов")
    private String password;

    @NotBlank(message = "Роль должена быть")
//    todo valid 'USER','ADMIN','MODERATOR'
    private RoleType roleType;


}
