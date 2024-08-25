package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FatsResearchCreateDto {
    @NotNull
    private Float patronMassBeforeExtraction;

    @NotNull
    private Float patronMassAfterExtraction;

    @NotNull
    private UUID probeId;
}
