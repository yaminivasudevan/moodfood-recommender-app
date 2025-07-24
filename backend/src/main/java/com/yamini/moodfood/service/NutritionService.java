package com.yamini.moodfood.service;

import com.yamini.moodfood.dto.NutritionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NutritionService {

    @Value("${usda_api_key}")
    private String USDA_API_KEY;

    private static final String USDA_API_URL = "https://api.nal.usda.gov/fdc/v1/foods/search";

    private final RestTemplate restTemplate = new RestTemplate();

    public NutritionInfo fetchNutritionData(String mealText) {
        if (mealText == null || mealText.trim().isEmpty()) {
            throw new IllegalArgumentException("Meal cannot be empty.");
        }

        String uri = UriComponentsBuilder.fromUriString(USDA_API_URL)
                .queryParam("api_key", USDA_API_KEY)
                .queryParam("query", mealText)
                .queryParam("pageSize", 1)
                .toUriString();

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("foods")) return emptyNutrition();

            List<Map<String, Object>> foods = (List<Map<String, Object>>) body.get("foods");
            if (foods == null || foods.isEmpty()) return emptyNutrition();

            Map<String, Object> firstFood = foods.get(0);
            List<Map<String, Object>> nutrients = (List<Map<String, Object>>) firstFood.get("foodNutrients");

            double calories = 0.0, protein = 0.0, fat = 0.0, carbs = 0.0;

            if (nutrients != null) {
                for (Map<String, Object> nutrient : nutrients) {
                    String name = (String) nutrient.get("nutrientName");
                    double value = nutrient.get("value") instanceof Number ? ((Number) nutrient.get("value")).doubleValue() : 0.0;

                    switch (name) {
                        case "Energy":
                            calories = value;
                            break;
                        case "Protein":
                            protein = value;
                            break;
                        case "Total lipid (fat)":
                            fat = value;
                            break;
                        case "Carbohydrate, by difference":
                            carbs = value;
                            break;
                    }
                }
            }

            return NutritionInfo.builder()
                    .calories(calories)
                    .protein(protein)
                    .fat(fat)
                    .carbs(carbs)
                    .build();

        } catch (HttpClientErrorException e) {
            log.error("HTTP error calling USDA API: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Unexpected error calling USDA API", e);
            throw e;
        }
    }

    private NutritionInfo emptyNutrition() {
        return NutritionInfo.builder()
                .calories(0.0)
                .protein(0.0)
                .fat(0.0)
                .carbs(0.0)
                .build();
    }
}
