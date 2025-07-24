package com.yamini.moodfood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    private Long fdcId;
    private String description;
    private List<FoodNutrient> foodNutrients;
}
