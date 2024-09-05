package ru.caloricity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IngredientInPageDto {
    private UUID id;

    private String name;

    private Float ediblePart;

    private Float water;

    private Float proteins;

    private Float fats;

    private Float carbohydrates;
}
