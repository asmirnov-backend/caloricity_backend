package ru.caloricity.proteinsresearch;

import java.util.UUID;

public record ProteinsResearchDto(
        UUID id,
        Double titrantVolumeParallelFirst,
        Double titrantVolumeParallelSecond,
        Double massNaveskiParallelFirst,
        Double massNaveskiParallelSecond,
        Double controlVolume,
        Double coefficient
) {
}
