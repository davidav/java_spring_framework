package com.example.webfluxexample.model;

import com.example.webfluxexample.entity.SubItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubItemModel {

    private String name;
    private BigDecimal price;

    public static SubItemModel from(SubItem subItem) {
        return new SubItemModel(subItem.getName(), subItem.getPrice());
    }

}
