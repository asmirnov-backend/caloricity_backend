package ru.caloricity.main.caloricity.drySubstancesResearch;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrySubstancesResearchCreateDto {
    private String name;

    private float ediblePart;

    private float water;

    private float proteins;

    private float fats;

    private float carbohydrates;
}
