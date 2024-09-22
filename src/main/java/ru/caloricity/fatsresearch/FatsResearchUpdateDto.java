package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public record FatsResearchUpdateDto(
        @NotNull Float patronMassBeforeExtractionParallelFirst,
        @NotNull Float patronMassBeforeExtractionParallelSecond,
        @NotNull Float patronMassAfterExtractionParallelFirst,
        @NotNull Float patronMassAfterExtractionParallelSecond
) {
}
