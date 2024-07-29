package ru.caloricity.main.caloricity.ingredientCatalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCatalogCreateDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private float ediblePart;

    @NotNull
    private float water;

    @NotNull
    private float proteins;

    @NotNull
    private float fats;

    @NotNull
    private float carbohydrates;
}
