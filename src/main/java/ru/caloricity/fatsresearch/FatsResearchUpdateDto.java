package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;

public record FatsResearchUpdateDto(
        @NotNull Float patronMassBeforeExtractionParallelFirst,
        @NotNull Float patronMassBeforeExtractionParallelSecond,
        @NotNull Float patronMassAfterExtractionParallelFirst,
        @NotNull Float patronMassAfterExtractionParallelSecond
) {
}
