package ru.caloricity.probe.research.fatsresearch;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FatsResearchCreateDto(
        @NotNull @Min(0) Double patronMassBeforeExtractionParallelFirst,
        @NotNull @Min(0) Double patronMassBeforeExtractionParallelSecond,
        @NotNull @Min(0) Double patronMassAfterExtractionParallelFirst,
        @NotNull @Min(0) Double patronMassAfterExtractionParallelSecond,
        @NotNull @Min(0) Double massNaveskiParallelFirst,
        @NotNull @Min(0) Double massNaveskiParallelSecond
) {
}
