package com.yamini.moodfood.dto;

import com.yamini.moodfood.model.MealLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealLogResponse {
    private Long id;
    private String meal;
    private String mood;
    private OffsetDateTime loggedAt;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;

    public MealLogResponse(MealLog mealLog) {
        this.id = mealLog.getId();
        this.meal = mealLog.getMeal();
        this.mood = mealLog.getMood();
        this.loggedAt = mealLog.getLoggedAt();
        this.calories = mealLog.getCalories();
        this.protein = mealLog.getProtein();
        this.fat = mealLog.getFat();
        this.carbs = mealLog.getCarbs();
    }

}
