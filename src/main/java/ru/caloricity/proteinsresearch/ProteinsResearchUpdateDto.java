package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;

public record ProteinsResearchUpdateDto(
        @NotNull Double titrantVolumeParallelFirst,
        @NotNull Double titrantVolumeParallelSecond,
        @NotNull Double controlVolume,
        @NotNull Double coefficient
) {
}
