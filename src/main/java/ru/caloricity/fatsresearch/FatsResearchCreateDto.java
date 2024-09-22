package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public record FatsResearchCreateDto(
        @NotNull @Min(0) Float patronMassBeforeExtractionParallelFirst,
        @NotNull @Min(0) Float patronMassBeforeExtractionParallelSecond,
        @NotNull @Min(0) Float patronMassAfterExtractionParallelFirst,
        @NotNull @Min(0) Float patronMassAfterExtractionParallelSecond,
        @NotNull UUID probeId
) {
}
