package ru.caloricity.probe.research.proteinsresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProteinsResearchCreateDto(
        @NotNull @Min(0) Double titrantVolumeParallelFirst,
        @NotNull @Min(0) Double titrantVolumeParallelSecond,
        @NotNull @Min(0) Double massNaveskiParallelFirst,
        @NotNull @Min(0) Double massNaveskiParallelSecond,
        @NotNull Double controlVolume,
        @NotNull Double coefficient
) {
}
