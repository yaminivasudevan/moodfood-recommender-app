package com.yamini.moodfood.dto;

import com.yamini.moodfood.model.MealLog;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MealLogResponse {
    private Long id;
    private String meal;
    private String mood;
    private LocalDateTime loggedAt;

    public MealLogResponse(MealLog mealLog) {
        this.id = mealLog.getId();
        this.meal = mealLog.getMeal();
        this.mood = mealLog.getMood();
        this.loggedAt = mealLog.getLoggedAt();
    }
}
