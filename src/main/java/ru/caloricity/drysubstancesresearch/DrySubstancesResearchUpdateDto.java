package ru.caloricity.drysubstancesresearch;

import jakarta.validation.constraints.NotNull;

public record DrySubstancesResearchUpdateDto(@NotNull
                                             Float byuksaParallelFirst,

                                             @NotNull
                                             Float byuksaParallelSecond,

                                             @NotNull
                                             Float byuksaAfterDryingParallelFirst,

                                             @NotNull
                                             Float byuksaAfterDryingParallelSecond) {

}
