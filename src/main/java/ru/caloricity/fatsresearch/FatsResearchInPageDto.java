package ru.caloricity.fatsresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FatsResearchInPageDto {
    private UUID id;

    private Float patronMassBeforeExtraction;

    private Float patronMassAfterExtraction;
}
