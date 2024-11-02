package ru.caloricity.ingredient;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IngredientInPageDto(UUID id,
                                  String name,
                                  Double ediblePart,
                                  Double water,
                                  Double proteins,
                                  Double fats,
                                  Double carbohydrates
) {
}
