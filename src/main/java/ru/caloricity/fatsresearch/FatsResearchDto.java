package ru.caloricity.fatsresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FatsResearchDto {
    private UUID id;

    private Float patronMassBeforeExtractionParallelFirst;

    private Float patronMassBeforeExtractionParallelSecond;

    private Float patronMassAfterExtractionParallelFirst;

    private Float patronMassAfterExtractionParallelSecond;

}
