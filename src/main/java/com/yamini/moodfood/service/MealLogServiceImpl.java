package com.yamini.moodfood.service;

import com.yamini.moodfood.model.MealLog;
import com.yamini.moodfood.repository.MealLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealLogServiceImpl implements MealLogService {

    @Autowired
    MealLogRepository mealLogRepository;

    @Override
    public MealLog saveMeal(String meal, String mood) {
        MealLog mealLog = MealLog.builder()
                .meal(meal)
                .mood(mood)
                .loggedAt(LocalDateTime.now())
                .build();
        return mealLogRepository.save(mealLog);
    }

    @Override
    public List<MealLog> getAllMeals() {
        return mealLogRepository.findAll();
    }

    @Override
    public List<MealLog> getMealByMood(String mood) {
        return mealLogRepository.findByMood(mood);
    }
}
