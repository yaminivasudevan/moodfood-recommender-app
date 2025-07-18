package com.yamini.moodfood.service.impl;

import com.yamini.moodfood.dto.MealLogRequest;
import com.yamini.moodfood.dto.MealLogResponse;
import com.yamini.moodfood.model.MealLog;
import com.yamini.moodfood.repository.MealLogRepository;
import com.yamini.moodfood.service.MealLogService;
import com.yamini.moodfood.specification.MealLogSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealLogServiceImpl implements MealLogService {

    @Autowired
    MealLogRepository mealLogRepository;

    @Override
    public MealLogResponse saveMeal(MealLogRequest mealLogRequest) {
        MealLog mealLog = MealLog.builder()
                .meal(mealLogRequest.getMeal())
                .mood(mealLogRequest.getMood())
                .loggedAt(LocalDateTime.now())
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
