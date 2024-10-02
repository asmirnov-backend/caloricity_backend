package ru.caloricity.fatsresearch;

import java.util.UUID;

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
