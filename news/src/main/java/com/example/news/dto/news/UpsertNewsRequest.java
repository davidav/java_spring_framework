package com.example.news.dto.news;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpsertNewsRequest {

    @NotNull(message = "userId должен быть заполнен")
    @Positive(message = "id больше нуля")
    private Long userId;

    @NotNull(message = "categoryId должен быть заполнен")
    @Positive(message = "id больше нуля")
    private Long categoryId;

    @NotBlank(message = "title должен быть")
    @Size(min = 3, max = 30, message = "Заголовок должен быть от {min} до {max} символов")
    private String title;

    @NotBlank(message = "text должен быть")
    @Size(min = 30, max = 300, message = "Текс должен быть от {min} до {max} символов")
    private String text;




}
