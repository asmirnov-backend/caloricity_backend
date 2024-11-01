package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FatsResearchUpdateDto(
        @NotNull Float patronMassBeforeExtractionParallelFirst,
        @NotNull Float patronMassBeforeExtractionParallelSecond,
        @NotNull Float patronMassAfterExtractionParallelFirst,
        @NotNull Float patronMassAfterExtractionParallelSecond
) {
}
