package ru.caloricity.drysubstancesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record DrySubstancesResearchCreateDto(@NotNull
                                             @Min(0)
                                             Float byuksaParallelFirst,

                                             @NotNull
                                             @Min(0)
                                             Float byuksaParallelSecond,

                                             @NotNull
                                             @Min(0)
                                             Float byuksaAfterDryingParallelFirst,

                                             @NotNull
                                             @Min(0)
                                             Float byuksaAfterDryingParallelSecond,

                                             @NotNull
                                             UUID probeId) {

}
