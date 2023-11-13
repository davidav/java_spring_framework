package com.example.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpsertOrderRequest {

    @NotNull(message = "id должен быть заполнен")
    @Positive(message = "id больше нуля")
    private Long clientId;

    @NotBlank(message = "имя должно быть заполнено")
    private String product;

    @NotNull(message = "должна быть цена")
    @Positive(message = "цена должна быть больше нуля")
    private BigDecimal cost;

}
