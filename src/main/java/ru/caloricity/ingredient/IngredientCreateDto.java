package ru.caloricity.ingredient;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record IngredientCreateDto(
        @NotNull @NotBlank @Size(min = 2) String name,
        @NotNull @Min(value = 0) @Max(value = 1) Double ediblePart,
        @NotNull @Min(value = 0) Double water,
        @NotNull @Min(value = 0) Double proteins,
        @NotNull @Min(value = 0) Double fats,
        @NotNull @Min(value = 0) Double carbohydrates
) {
}
