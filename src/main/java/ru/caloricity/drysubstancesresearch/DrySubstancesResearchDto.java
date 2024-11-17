package ru.caloricity.drysubstancesresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
public record DrySubstancesResearchDto(UUID id,
                                       Double byuksaParallelFirst,
                                       Double byuksaParallelSecond,
                                       Double byuksaAfterDryingParallelFirst,
                                       Double byuksaAfterDryingParallelSecond,
                                       Double massNaveskiParallelFirst,
                                       Double massNaveskiParallelSecond,
                                       Double dryResidueWeightParallelFirst,
                                       Double dryResidueWeightParallelSecond,
                                       Double dryResidueWeightAverage
) {
}
