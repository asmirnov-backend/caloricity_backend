package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProteinsResearchCreateDto(
    @NotNull Float titrantVolumeParallelFirst,
    @NotNull Float titrantVolumeParallelSecond,
    @NotNull Float controlVolume,
    @NotNull Float coefficient,
    @NotNull UUID probeId
) {}
