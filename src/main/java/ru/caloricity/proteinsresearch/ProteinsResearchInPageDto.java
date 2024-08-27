package ru.caloricity.proteinsresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProteinsResearchInPageDto {
    private UUID id;

    private Float titrantVolume;

    private Float mass;

    private Float controlVolume;

    private Float coefficient;
}
