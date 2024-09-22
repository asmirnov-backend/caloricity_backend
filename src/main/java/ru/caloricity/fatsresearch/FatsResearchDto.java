package ru.caloricity.fatsresearch;

import lombok.Getter;

import java.util.UUID;

@Getter
public record FatsResearchDto(UUID id,
                              Float patronMassBeforeExtractionParallelFirst,
                              Float patronMassBeforeExtractionParallelSecond,
                              Float patronMassAfterExtractionParallelFirst,
                              Float patronMassAfterExtractionParallelSecond) {
}
