package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FatsResearchUpdateDto {
    @NotNull
    private Float patronMassBeforeExtraction;

    @NotNull
    private Float patronMassAfterExtraction;
}
