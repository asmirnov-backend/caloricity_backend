package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ProteinsResearchCreateDto(
        @NotNull Double titrantVolumeParallelFirst,
        @NotNull Double titrantVolumeParallelSecond,
        @NotNull Double controlVolume,
        @NotNull Double coefficient,
        @NotNull UUID probeId
) {
}
