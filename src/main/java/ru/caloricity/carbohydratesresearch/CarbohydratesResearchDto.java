package ru.caloricity.carbohydratesresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
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
