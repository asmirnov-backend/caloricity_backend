package ru.caloricity.probe.research.fatsresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FatsResearchDto(UUID id,
                              Double patronMassBeforeExtractionParallelFirst,
                              Double patronMassBeforeExtractionParallelSecond,
                              Double patronMassAfterExtractionParallelFirst,
                              Double patronMassAfterExtractionParallelSecond,
                              Double massNaveskiParallelFirst,
                              Double massNaveskiParallelSecond,
                              Double getMassForOneGramAverage
) {
}
