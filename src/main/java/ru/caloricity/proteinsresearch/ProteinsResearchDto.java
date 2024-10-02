package ru.caloricity.proteinsresearch;

import java.util.UUID;

public record ProteinsResearchDto(UUID id,
                                  Float titrantVolumeParallelFirst,
                                  Float titrantVolumeParallelSecond,
                                  Float controlVolume,
                                  Float coefficient
) {
}
