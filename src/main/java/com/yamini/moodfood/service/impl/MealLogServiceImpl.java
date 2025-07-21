package com.yamini.moodfood.service.impl;

import com.yamini.moodfood.dto.MealLogRequest;
import com.yamini.moodfood.dto.MealLogResponse;
import com.yamini.moodfood.dto.NutritionInfo;
import com.yamini.moodfood.model.MealLog;
import com.yamini.moodfood.repository.MealLogRepository;
import com.yamini.moodfood.service.MealLogService;
import com.yamini.moodfood.service.NutritionService;
import com.yamini.moodfood.specification.MealLogSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
public class MealLogServiceImpl implements MealLogService {

    @Autowired
    MealLogRepository mealLogRepository;

    @Autowired
    NutritionService nutritionService;

    @Override
    public MealLogResponse saveMeal(MealLogRequest mealLogRequest) {
        NutritionInfo nutrition;
        try {
            nutrition = nutritionService.fetchNutritionData(mealLogRequest.getMeal());
        } catch (Exception e) {
            // Log error, fallback to zero nutrition info so meal logging still works
            log.error("Nutrition API failed, saving meal without nutrition data", e);
            nutrition = NutritionInfo.builder()
                    .calories(0.0).protein(0.0).fat(0.0).carbs(0.0).build();
        }

        MealLog mealLog = MealLog.builder()
                .meal(mealLogRequest.getMeal())
                .mood(mealLogRequest.getMood())
                .loggedAt(OffsetDateTime.now())
                .calories(nutrition.getCalories())
                .protein(nutrition.getProtein())
                .fat(nutrition.getFat())
                .carbs(nutrition.getCarbs())
                .build();

        MealLog saved = mealLogRepository.save(mealLog);
        return new MealLogResponse(saved);
    }

    @Override
    public List<MealLogResponse> getAllMeals() {
        return mealLogRepository.findAll()
                .stream()
                .map(MealLogResponse::new)
                .toList();
    }

    @Override
    public List<MealLogResponse> getMealByMood(String mood) {
        return mealLogRepository.findByMood(mood)
                .stream()
                .map(MealLogResponse::new)
                .toList();
    }

    @Override
    public Page<MealLogResponse> getMealHistory(
            String meal, String mood, LocalDateTime fromDate, LocalDateTime toDate,
            int page, int size, String sortBy, String direction) {

        Specification<MealLog> spec = MealLogSpecification.mealContains(meal)
                .and(MealLogSpecification.moodEquals(mood))
                .and(MealLogSpecification.loggedAtBetween(fromDate, toDate));

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (Exception e) {
            sortDirection = Sort.Direction.ASC; // default
        }

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "loggedAt";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<MealLog> mealLogs = mealLogRepository.findAll(spec, pageable);

        return mealLogs.map(MealLogResponse::new);
    }
}
