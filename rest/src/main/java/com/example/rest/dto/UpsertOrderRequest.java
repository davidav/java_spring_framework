package com.example.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertOrderRequest {

    @NotNull(message = "id должен быть заполнен")
    @Positive(message = "id больше нуля")
    private Long clientId;

    @NotBlank(message = "продукт должн быть заполнен")
    private String product;

    @NotNull(message = "должна быть цена")
    @Positive(message = "цена должна быть больше нуля")
    private BigDecimal cost;

}
