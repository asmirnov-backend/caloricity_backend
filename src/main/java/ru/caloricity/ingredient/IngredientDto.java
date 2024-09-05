package ru.caloricity.ingredient;

import java.util.UUID;

public record IngredientDto(UUID id, String name, Float ediblePart,
                            Float water, Float proteins, Float fats,
                            Float carbohydrates) {
}
