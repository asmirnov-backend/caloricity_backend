package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.NotNull;

public record CarbohydratesResearchUpdateDto(
        @NotNull Double byuksaParallelFirst,
        @NotNull Double byuksaParallelSecond,
        @NotNull Double byuksaAfterDryingParallelFirst,
        @NotNull Double byuksaAfterDryingParallelSecond
) {
}
