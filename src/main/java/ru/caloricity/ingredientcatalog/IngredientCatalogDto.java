package ru.caloricity.ingredientcatalog;

import java.util.UUID;

public record IngredientCatalogDto(UUID id, String name, Float ediblePart,
                                   Float water, Float proteins, Float fats,
                                   Float carbohydrates) {}
