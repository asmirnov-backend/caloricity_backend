package ru.caloricity.drysubstancesresearch;

import jakarta.validation.constraints.NotNull;

public record DrySubstancesResearchUpdateDto(@NotNull Double byuksaParallelFirst,
                                             @NotNull Double byuksaParallelSecond,
                                             @NotNull Double byuksaAfterDryingParallelFirst,
                                             @NotNull Double byuksaAfterDryingParallelSecond
) {
}
