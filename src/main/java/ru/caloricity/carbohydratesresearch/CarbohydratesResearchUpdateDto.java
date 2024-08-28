package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.NotNull;

public record CarbohydratesResearchUpdateDto(
        @NotNull Float byuksaParallelFirst,
        @NotNull Float byuksaParallelSecond,
        @NotNull Float byuksaAfterDryingParallelFirst,
        @NotNull Float byuksaAfterDryingParallelSecond
) {
}
