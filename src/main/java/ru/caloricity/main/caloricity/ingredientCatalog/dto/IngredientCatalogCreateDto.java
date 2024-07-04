package ru.caloricity.main.caloricity.ingredientCatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCatalogCreateDto {
    private String name;

    private float ediblePart;

    private float water;

    private float proteins;

    private float fats;

    private float carbohydrates;
}
