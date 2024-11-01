package ru.caloricity.ingredient;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IngredientDto(UUID id,
                            String name,
                            Float ediblePart,
                            Float water,
                            Float proteins,
                            Float fats,
                            Float carbohydrates
) {
}
