package ru.caloricity.ingredient;

import jakarta.validation.constraints.*;

public record IngredientCreateDto(
        @NotNull @NotBlank @Size(min = 2) String name,
        @NotNull @Min(value = 0) @Max(value = 1) Float ediblePart,
        @NotNull @Min(value = 0) Float water,
        @NotNull @Min(value = 0) Float proteins,
        @NotNull @Min(value = 0) Float fats,
        @NotNull @Min(value = 0) Float carbohydrates
) {
}
