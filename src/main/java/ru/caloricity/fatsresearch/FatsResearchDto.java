package ru.caloricity.fatsresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FatsResearchDto(UUID id,
                              Float patronMassBeforeExtractionParallelFirst,
                              Float patronMassBeforeExtractionParallelSecond,
                              Float patronMassAfterExtractionParallelFirst,
                              Float patronMassAfterExtractionParallelSecond,
                              Float dryResidueWeightParallelFirst,
                              Float dryResidueWeightParallelSecond,
                              Float dryResidueWeightAverage
) {
}
