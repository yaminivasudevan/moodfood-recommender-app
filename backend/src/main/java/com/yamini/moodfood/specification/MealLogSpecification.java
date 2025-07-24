package com.yamini.moodfood.specification;

import com.yamini.moodfood.model.MealLog;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class MealLogSpecification {

    public static Specification<MealLog> mealContains(String meal) {
        return (root, query, builder) ->
                meal == null ? builder.conjunction() :
                        builder.like(builder.lower(root.get("meal")), "%" + meal.toLowerCase() + "%");
    }

    public static Specification<MealLog> moodEquals(String mood) {
        return (root, query, builder) ->
                mood == null ? builder.conjunction() :
                        builder.equal(builder.lower(root.get("mood")), mood.toLowerCase());
    }

    public static Specification<MealLog> loggedAtBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, builder) -> {
            if (from == null && to == null) return builder.conjunction();
            if (from == null) return builder.lessThanOrEqualTo(root.get("loggedAt"), to);
            if (to == null) return builder.greaterThanOrEqualTo(root.get("loggedAt"), from);
            return builder.between(root.get("loggedAt"), from, to);
        };
    }
}
