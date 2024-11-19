package ru.caloricity.probe.research.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeRepository;

@Service
@RequiredArgsConstructor
public class ProteinsResearchMapper {
    private final ProbeRepository probeRepository;

    public ProteinsResearch toEntity(@NotNull ProteinsResearchCreateDto dto) {
        return ProteinsResearch.builder()
                .titrantVolumeParallelFirst(dto.titrantVolumeParallelFirst())
                .titrantVolumeParallelSecond(dto.titrantVolumeParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .controlVolume(dto.controlVolume())
                .coefficient(dto.coefficient())
                .probe(probeRepository.getReferenceById(dto.probeId()))
                .build();
    }

    public ProteinsResearchDto toDto(ProteinsResearch entity) {
        if (entity == null) {
            return null;
        }

        return ProteinsResearchDto.builder()
                .id(entity.getId())
                .titrantVolumeParallelFirst(entity.getTitrantVolumeParallelFirst())
                .titrantVolumeParallelSecond(entity.getTitrantVolumeParallelSecond())
                .coefficient(entity.getCoefficient())
                .controlVolume(entity.getControlVolume())
                .massNaveskiParallelFirst(entity.getMassNaveskiParallelFirst())
                .massNaveskiParallelSecond(entity.getMassNaveskiParallelSecond())
                .build();
    }

    void updateEntity(@NotNull ProteinsResearch entity, @NotNull ProteinsResearchUpdateDto dto) {
        entity.setTitrantVolumeParallelFirst(dto.titrantVolumeParallelFirst());
        entity.setTitrantVolumeParallelSecond(dto.titrantVolumeParallelSecond());
        entity.setMassNaveskiParallelFirst(dto.massNaveskiParallelFirst());
        entity.setMassNaveskiParallelSecond(dto.massNaveskiParallelSecond());
        entity.setControlVolume(dto.controlVolume());
        entity.setCoefficient(dto.coefficient());
    }
}
