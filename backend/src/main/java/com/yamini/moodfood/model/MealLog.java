package com.yamini.moodfood.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="meal_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String meal;
    private String mood;

    @Column(name = "logged_at")
    private OffsetDateTime loggedAt;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fat;
}
