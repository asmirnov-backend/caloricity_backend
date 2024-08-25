package ru.caloricity.fatsResearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FatsResearchDto {
    private UUID id;

    private Float patronMassBeforeExtraction;

    private Float patronMassAfterExtraction;

}
