package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;

public record ProteinsResearchUpdateDto(
    @NotNull Float titrantVolumeParallelFirst,
    @NotNull Float titrantVolumeParallelSecond,
    @NotNull Float controlVolume,
    @NotNull Float coefficient
) {}
