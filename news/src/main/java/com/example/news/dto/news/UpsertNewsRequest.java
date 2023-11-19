package com.example.news.dto.news;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpsertNewsRequest {

    @NotBlank(message = "Заголовок должен быть")
    @Size(min = 3, max = 30, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;

    @NotBlank(message = "Текс должен быть")
    @Size(min = 30, max = 300, message = "Текс должен быть от {min} до {max} символов")
    private String text;

}
