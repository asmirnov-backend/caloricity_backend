package ru.caloricity.main.caloricity.drySubstancesResearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DrySubstancesResearchInPageDto {
    private UUID id;

    private float byuksaParallelFirst;

    private float byuksaParallelSecond;

    private float bankaEmptyMass;

    private float mass;
}
