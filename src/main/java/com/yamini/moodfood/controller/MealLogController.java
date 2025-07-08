package com.yamini.moodfood.controller;

import com.yamini.moodfood.dto.ApiResponse;
import com.yamini.moodfood.dto.MealRequest;
import com.yamini.moodfood.model.MealLog;
import com.yamini.moodfood.service.MealLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealLogController {

    @Autowired
    MealLogService mealLogService;

    @PostMapping
    public ApiResponse<MealLog> logMeal(@Valid @RequestBody MealRequest mealRequest) {
        MealLog savedMeal = mealLogService.saveMeal(mealRequest.getMeal(), mealRequest.getMood());
        return new ApiResponse<>("success", savedMeal, "Meal logged successfully");
    }

    @GetMapping
    public ApiResponse<List<MealLog>> getAllMeals(){
        List<MealLog> allMeals = mealLogService.getAllMeals();
        return new ApiResponse<>("success", allMeals, "Meals fetched successfully");
    }

    @GetMapping("/filter")
    public ApiResponse<List<MealLog>> getMealByMood(@RequestParam String mood){
        List<MealLog> filteredMealLogs = mealLogService.getMealByMood(mood);
        return new ApiResponse<>("success", filteredMealLogs, "Meal with mood: "+mood);
    }
}