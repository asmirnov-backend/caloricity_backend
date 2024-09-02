package ru.caloricity.ingredientcatalog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredientCatalogCreateDto(
        @NotNull @NotBlank @Size(min = 2) String name,
        @NotNull @Min(value = 0) Float ediblePart,
        @NotNull @Min(value = 0) Float water,
        @NotNull @Min(value = 0) Float proteins,
        @NotNull @Min(value = 0) Float fats,
        @NotNull @Min(value = 0) Float carbohydrates
) {
}
