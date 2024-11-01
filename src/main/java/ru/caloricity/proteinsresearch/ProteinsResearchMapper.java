package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeService;

@Service
@RequiredArgsConstructor
public class ProteinsResearchMapper {
    private final ProbeService probeService;

    public ProteinsResearch toEntity(@NotNull ProteinsResearchCreateDto dto) {
        return ProteinsResearch.builder()
                .titrantVolumeParallelFirst(dto.titrantVolumeParallelFirst())
                .titrantVolumeParallelSecond(dto.titrantVolumeParallelSecond())
                .controlVolume(dto.controlVolume())
                .coefficient(dto.coefficient())
                .probe(probeService.getExistingReferenceByIdOrThrow(dto.probeId()))
                .build();
    }
}
