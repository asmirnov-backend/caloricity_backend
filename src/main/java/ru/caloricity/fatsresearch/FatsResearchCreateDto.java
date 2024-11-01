package ru.caloricity.fatsresearch;

@Builder
import lombok.Builder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FatsResearchCreateDto(
        @NotNull @Min(0) Float patronMassBeforeExtractionParallelFirst,
        @NotNull @Min(0) Float patronMassBeforeExtractionParallelSecond,
        @NotNull @Min(0) Float patronMassAfterExtractionParallelFirst,
        @NotNull @Min(0) Float patronMassAfterExtractionParallelSecond,
        @NotNull UUID probeId
) {
}
