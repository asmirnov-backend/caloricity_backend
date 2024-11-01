package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProteinsResearchCreateDto(
        @NotNull Float titrantVolumeParallelFirst,
        @NotNull Float titrantVolumeParallelSecond,
        @NotNull Float controlVolume,
        @NotNull Float coefficient,
        @NotNull UUID probeId
) {
}
