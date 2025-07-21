package com.yamini.moodfood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodNutrient {
    private Integer nutrientId;
    private String nutrientName;
    private String unitName;
    private Double value;
}
