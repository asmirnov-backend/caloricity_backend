package ru.caloricity.probeingredient;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record ProbeIngredientUpdateDto(
        @Nullable @Min(0) Float gross,
        @Nullable @Min(0) Float net
) {
}
