package ru.caloricity.probe.research.drysubstancesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DrySubstancesResearchCreateDto(
        @NotNull @Min(0) Double byuksaParallelFirst,
        @NotNull @Min(0) Double byuksaParallelSecond,
        @NotNull @Min(0) Double byuksaAfterDryingParallelFirst,
        @NotNull @Min(0) Double byuksaAfterDryingParallelSecond,
        @NotNull @Min(0) Double massNaveskiParallelFirst,
        @NotNull @Min(0) Double massNaveskiParallelSecond
) {
}
