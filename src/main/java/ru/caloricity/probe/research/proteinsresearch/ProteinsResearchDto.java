package ru.caloricity.probe.research.proteinsresearch;

import lombok.Builder;

import java.util.UUID;

@Builder
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
