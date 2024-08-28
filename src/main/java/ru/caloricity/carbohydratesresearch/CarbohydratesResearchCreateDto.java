package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarbohydratesResearchCreateDto(
        @NotNull Float byuksaParallelFirst,
        @NotNull Float byuksaParallelSecond,
        @NotNull Float byuksaAfterDryingParallelFirst,
        @NotNull Float byuksaAfterDryingParallelSecond,
        @NotNull UUID probeId
) {
}
