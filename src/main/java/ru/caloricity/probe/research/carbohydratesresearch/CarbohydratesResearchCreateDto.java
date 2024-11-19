package ru.caloricity.probe.research.carbohydratesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CarbohydratesResearchCreateDto(
        @NotNull @Min(0) Double byuksaParallelFirst,
        @NotNull @Min(0) Double byuksaParallelSecond,
        @NotNull @Min(0) Double byuksaAfterDryingParallelFirst,
        @NotNull @Min(0) Double byuksaAfterDryingParallelSecond,
        @NotNull @Min(0) Double massNaveskiParallelFirst,
        @NotNull @Min(0) Double massNaveskiParallelSecond
) {
}
