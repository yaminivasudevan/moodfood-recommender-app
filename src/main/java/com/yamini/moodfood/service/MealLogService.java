package com.yamini.moodfood.service;

import com.yamini.moodfood.dto.MealLogRequest;
import com.yamini.moodfood.dto.MealLogResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface MealLogService {

MealLogResponse saveMeal(MealLogRequest mealRequest);
List<MealLogResponse> getAllMeals();
List<MealLogResponse> getMealByMood(String mood);
Page<MealLogResponse> getMealHistory(String meal, String mood, LocalDateTime fromDate, LocalDateTime toDate, int page, int size, String sortBy, String direction);
}
