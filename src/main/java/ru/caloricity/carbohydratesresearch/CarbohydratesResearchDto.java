package ru.caloricity.carbohydratesresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CarbohydratesResearchDto {
    private UUID id;

    private Float byuksaParallelFirst;

    private Float byuksaParallelSecond;

    private Float byuksaAfterDryingParallelFirst;

    private Float byuksaAfterDryingParallelSecond;
}
