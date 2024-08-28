package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProteinsResearchCreateDto {
    @NotNull
    private Float titrantVolumeParallelFirst;

    @NotNull
    private Float titrantVolumeParallelSecond;

    @NotNull
    private Float controlVolume;

    @NotNull
    private Float coefficient;

    @NotNull
    private UUID probeId;
}
