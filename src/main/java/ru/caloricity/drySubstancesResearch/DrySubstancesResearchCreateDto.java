package ru.caloricity.drySubstancesResearch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrySubstancesResearchCreateDto {
    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private Float ediblePart;

    @NotNull
    private Float water;

    @NotNull
    private Float proteins;

    @NotNull
    private Float fats;

    @NotNull
    private Float carbohydrates;
}
