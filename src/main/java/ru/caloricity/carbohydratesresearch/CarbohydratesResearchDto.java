package ru.caloricity.carbohydratesresearch;

import java.util.UUID;

public record CarbohydratesResearchDto(UUID id,
                                       Float byuksaParallelFirst,
                                       Float byuksaParallelSecond,
                                       Float byuksaAfterDryingParallelFirst,
                                       Float byuksaAfterDryingParallelSecond,
                                       Float dryResidueWeightParallelFirst,
                                       Float dryResidueWeightParallelSecond,
                                       Float dryResidueWeightAverage
) {
}
