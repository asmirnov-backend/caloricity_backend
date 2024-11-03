package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CarbohydratesResearchCreateDto(
        @NotNull @Min(0) Double byuksaParallelFirst,
        @NotNull @Min(0) Double byuksaParallelSecond,
        @NotNull @Min(0) Double byuksaAfterDryingParallelFirst,
        @NotNull @Min(0) Double byuksaAfterDryingParallelSecond,
        @NotNull UUID probeId
) {
}
