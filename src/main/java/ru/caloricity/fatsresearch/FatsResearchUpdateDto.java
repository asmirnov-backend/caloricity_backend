package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FatsResearchUpdateDto(
        @NotNull Double patronMassBeforeExtractionParallelFirst,
        @NotNull Double patronMassBeforeExtractionParallelSecond,
        @NotNull Double patronMassAfterExtractionParallelFirst,
        @NotNull Double patronMassAfterExtractionParallelSecond
) {
}
