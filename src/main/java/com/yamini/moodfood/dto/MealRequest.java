package com.yamini.moodfood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealRequest {

    @NotBlank
    private String meal;

    @NotBlank
    private String mood;
}
