package com.yamini.moodfood.service;

import com.yamini.moodfood.dto.NutritionInfo;
import com.yamini.moodfood.exception.EdamamRateLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NutritionService {

    @Value("${edamam.app-id}")
    private String appId;

    @Value("${edamam.app-key}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public NutritionInfo fetchNutritionData(String mealText) {
        String url = String.format(
                "https://api.edamam.com/api/nutrition-details?app_id=%s&app_key=%s", appId, appKey
        );

        if (mealText == null || mealText.trim().isEmpty()) {
            throw new IllegalArgumentException("Meal cannot be empty.");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", mealText);
        requestBody.put("ingr", Collections.singletonList(mealText));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> body = response.getBody();
            log.info("Edamam API status: {}", response.getStatusCode());
            log.info("Edamam API response body: {}", body);

            if (body != null) {
                List<Map<String, Object>> ingredients = (List<Map<String, Object>>) body.get("ingredients");

                if (ingredients != null && !ingredients.isEmpty()) {
                    Map<String, Object> firstIngredient = ingredients.get(0);
                    List<Map<String, Object>> parsed = (List<Map<String, Object>>) firstIngredient.get("parsed");

                    if (parsed != null && !parsed.isEmpty()) {
                        Map<String, Object> firstParsed = parsed.get(0);
                        Map<String, Object> nutrients = (Map<String, Object>) firstParsed.get("nutrients");

                        double calories = extractNutrientValue(nutrients, "ENERC_KCAL");
                        double protein = extractNutrientValue(nutrients, "PROCNT");
                        double fat = extractNutrientValue(nutrients, "FAT");
                        double carbs = extractNutrientValue(nutrients, "CHOCDF");

                        log.info("Calories: {}", calories);

                        return NutritionInfo.builder()
                                .calories(calories)
                                .protein(protein)
                                .fat(fat)
                                .carbs(carbs)
                                .build();
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                log.error("Edamam API rate limit exceeded: {}", e.getResponseBodyAsString());
                throw new EdamamRateLimitException("API rate limit exceeded. Please try again later.");
            } else {
                log.error("Edamam API error: {}", e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            log.error("Unexpected error from Edamam API", e);
            throw e;
        }

        // Return default NutritionInfo if no data found
        return NutritionInfo.builder()
                .calories(0.0)
                .protein(0.0)
                .fat(0.0)
                .carbs(0.0)
                .build();
    }

    private Double extractNutrientValue(Map<String, Object> totalNutrients, String nutrientKey) {
        if (totalNutrients == null || !totalNutrients.containsKey(nutrientKey)) {
            return 0.0;
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> nutrient = (Map<String, Object>) totalNutrients.get(nutrientKey);
        if (nutrient == null || !nutrient.containsKey("quantity")) {
            return 0.0;
        }
        Object quantityObj = nutrient.get("quantity");
        if (quantityObj instanceof Number) {
            return ((Number) quantityObj).doubleValue();
        } else {
            return 0.0;
        }
    }
}
