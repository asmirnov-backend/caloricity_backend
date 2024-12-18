package ru.caloricity.probeingredient;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProbeIngredientCreateDto(
        @NotNull UUID probeId,
        @NotNull UUID ingredientId,
        @Nullable @Min(0) Double gross,
        @Nullable @Min(0) Double net
) {
}
