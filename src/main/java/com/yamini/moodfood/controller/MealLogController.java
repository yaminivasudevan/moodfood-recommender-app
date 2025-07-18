package com.yamini.moodfood.controller;

import com.yamini.moodfood.dto.MealLogResponse;
import com.yamini.moodfood.payload.ApiResponse;
import com.yamini.moodfood.dto.MealLogRequest;
import com.yamini.moodfood.service.MealLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealLogController {

    @Autowired
    MealLogService mealLogService;

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<MealLogResponse>> logMeal(@Valid @RequestBody MealLogRequest mealRequest) {
        MealLogResponse responseDTO = mealLogService.saveMeal(mealRequest);
        ApiResponse<MealLogResponse> response = new ApiResponse<>(true, responseDTO, "Meal logged successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllMeals")
    public ResponseEntity<ApiResponse<List<MealLogResponse>>>getAllMeals(){
        List<MealLogResponse> responseDTOs = mealLogService.getAllMeals();
        ApiResponse<List<MealLogResponse>> response = new ApiResponse<>(true, responseDTOs, "Meals fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<MealLogResponse>>> getMealByMood(@RequestParam String mood){
        List<MealLogResponse> responseDTOs = mealLogService.getMealByMood(mood);
        ApiResponse<List<MealLogResponse>> response = new ApiResponse<>(true, responseDTOs, "Meal with mood: " +mood + " fetched successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<Page<MealLogResponse>>> getMealHistory(
            @RequestParam(required = false) String meal,
            @RequestParam(required = false) String mood,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "loggedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {
        Page<MealLogResponse> result = mealLogService.getMealHistory(
                meal, mood, fromDate, toDate, page, size, sortBy, direction
        );
        ApiResponse<Page<MealLogResponse>> response = new ApiResponse<>(
                true, result, "Filtered meal history fetched successfully"
        );
        return ResponseEntity.ok(response);
    }
}