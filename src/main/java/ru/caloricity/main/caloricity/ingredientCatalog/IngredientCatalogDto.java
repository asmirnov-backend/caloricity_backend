package ru.caloricity.main.caloricity.ingredientCatalog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IngredientCatalogDto {
    private UUID id;
    
    private String name;

    private float ediblePart;

    private float water;

    private float proteins;

    private float fats;

    private float carbohydrates;
}
