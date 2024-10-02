package ru.caloricity.drysubstancesresearch;

import java.util.UUID;


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
