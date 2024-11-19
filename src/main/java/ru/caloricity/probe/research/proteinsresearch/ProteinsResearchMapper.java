package ru.caloricity.probe.research.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProteinsResearchMapper {
    public ProteinsResearch toEntity(ProteinsResearchCreateDto dto) {
        if (dto == null) {
            return null;
        }

        return ProteinsResearch.builder()
                .titrantVolumeParallelFirst(dto.titrantVolumeParallelFirst())
                .titrantVolumeParallelSecond(dto.titrantVolumeParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .controlVolume(dto.controlVolume())
                .coefficient(dto.coefficient())
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
