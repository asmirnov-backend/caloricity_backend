package ru.caloricity.drysubstancesresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrySubstancesResearchUpdateDto {
    @NotNull
    private Float byuksaParallelFirst;

    @NotNull
    private Float byuksaParallelSecond;

    @NotNull
    private Float bankaEmptyMass;

    @NotNull
    private Float bankaWithProbeMass;

    @NotNull
    private Float mass;
}
