package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FatsResearchCreateDto(
    @NotNull Float patronMassBeforeExtractionParallelFirst,
    @NotNull Float patronMassBeforeExtractionParallelSecond,
    @NotNull Float patronMassAfterExtractionParallelFirst,
    @NotNull Float patronMassAfterExtractionParallelSecond,
    @NotNull UUID probeId
) {}
