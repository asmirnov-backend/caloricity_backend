package ru.caloricity.drysubstancesresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DrySubstancesResearchDto(UUID id,
                                       Float byuksaParallelFirst,
                                       Float byuksaParallelSecond,
                                       Float byuksaAfterDryingParallelFirst,
                                       Float byuksaAfterDryingParallelSecond,
                                       Float dryResidueWeightParallelFirst,
                                       Float dryResidueWeightParallelSecond,
                                       Float dryResidueWeightAverage
) {
}
