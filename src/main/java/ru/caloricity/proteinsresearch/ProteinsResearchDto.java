package ru.caloricity.proteinsresearch;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProteinsResearchDto {
    private UUID id;

    private Float titrantVolumeParallelFirst;

    private Float titrantVolumeParallelSecond;

    private Float controlVolume;

    private Float coefficient;
}
