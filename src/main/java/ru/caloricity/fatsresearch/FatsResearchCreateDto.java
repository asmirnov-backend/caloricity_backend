package ru.caloricity.fatsresearch;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FatsResearchCreateDto(
        @NotNull @Min(0) Double patronMassBeforeExtractionParallelFirst,
        @NotNull @Min(0) Double patronMassBeforeExtractionParallelSecond,
        @NotNull @Min(0) Double patronMassAfterExtractionParallelFirst,
        @NotNull @Min(0) Double patronMassAfterExtractionParallelSecond,
        @NotNull UUID probeId
) {
}
