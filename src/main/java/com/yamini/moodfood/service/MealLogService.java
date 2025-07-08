package com.yamini.moodfood.service;

import com.yamini.moodfood.model.MealLog;
import java.util.List;

public interface MealLogService {

MealLog saveMeal(String meal, String mood);
List<MealLog> getAllMeals();
List<MealLog> getMealByMood(String mood);
}
