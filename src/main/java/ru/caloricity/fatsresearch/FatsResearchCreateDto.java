package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FatsResearchCreateDto {
    @NotNull
    private Float patronMassBeforeExtractionParallelFirst;

    @NotNull
    private Float patronMassBeforeExtractionParallelSecond;

    @NotNull
    private Float patronMassAfterExtractionParallelFirst;

    @NotNull
    private Float patronMassAfterExtractionParallelSecond;

    @NotNull
    private UUID probeId;
}
