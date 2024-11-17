package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CarbohydratesResearchUpdateDto(
        @NotNull Double byuksaParallelFirst,
        @NotNull Double byuksaParallelSecond,
        @NotNull Double byuksaAfterDryingParallelFirst,
        @NotNull Double byuksaAfterDryingParallelSecond,
        @NotNull @Min(0) Double massNaveskiParallelFirst,
        @NotNull @Min(0) Double massNaveskiParallelSecond
) {
}
