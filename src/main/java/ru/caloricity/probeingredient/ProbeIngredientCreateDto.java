package ru.caloricity.probeingredient;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProbeIngredientCreateDto(
        @NotNull UUID probeId,
        @NotNull UUID ingredientId,
        @Nullable @Min(0) Float gross,
        @Nullable @Min(0) Float net
) {
}
