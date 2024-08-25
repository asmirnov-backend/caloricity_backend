package ru.caloricity.drysubstancesresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DrySubstancesResearchInPageDto {
    private UUID id;

    private Float byuksaParallelFirst;

    private Float byuksaParallelSecond;

    private Float bankaEmptyMass;

    private Float bankaWithProbeMass;

    private Float mass;
}
