package com.yamini.moodfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data // includes @Getter, @Setter, @ToString, @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class MealLogRequest {

    @NotBlank(message = "Meal can't be blank!")
    private String meal;
    @NotBlank(message = "Mood can't be blank!")
    private String mood;
    private LocalDateTime loggedAt;
}
