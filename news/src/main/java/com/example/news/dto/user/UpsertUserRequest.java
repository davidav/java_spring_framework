package com.example.news.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {

    @NotBlank(message = "Имя должно быть заполнено")
    @Size(min = 3, max = 30, message = "Имя должно быть от {min} до {max} символов")
    private String firstName;

    @NotBlank(message = "Фамилия должна быть заполнено")
    @Size(min = 3, max = 30, message = "Фамилия должна быть от {min} до {max} символов")
    private String secondName;


}
