package ru.caloricity.carbohydratesResearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CarbohydratesResearchCreateDto {
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

    @NotNull
    private UUID probeId;
}
