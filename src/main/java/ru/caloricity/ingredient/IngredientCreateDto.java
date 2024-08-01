package ru.caloricity.ingredient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IngredientCreateDto(
        @NotNull @Min(0) Float gross,
        @NotNull @Min(0) Float net,
        @NotNull UUID ingredientInCatalogId,
        @NotNull UUID probeId
) {}
