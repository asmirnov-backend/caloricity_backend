package ru.caloricity.probe.research.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FatsResearchMapper {
    public FatsResearch toEntity(FatsResearchCreateDto dto) {
        if (dto == null) {
            return null;
        }

        return FatsResearch.builder()
                .patronMassBeforeExtractionParallelFirst(dto.patronMassBeforeExtractionParallelFirst())
                .patronMassBeforeExtractionParallelSecond(dto.patronMassBeforeExtractionParallelSecond())
                .patronMassAfterExtractionParallelFirst(dto.patronMassAfterExtractionParallelFirst())
                .patronMassAfterExtractionParallelSecond(dto.patronMassAfterExtractionParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .build();
    }

    public FatsResearchDto toDto(FatsResearch entity) {
        if (entity == null) {
            return null;
        }

        return FatsResearchDto.builder()
                .id(entity.getId())
                .patronMassBeforeExtractionParallelFirst(entity.getPatronMassBeforeExtractionParallelFirst())
                .patronMassBeforeExtractionParallelSecond(entity.getPatronMassBeforeExtractionParallelSecond())
                .patronMassAfterExtractionParallelFirst(entity.getPatronMassAfterExtractionParallelFirst())
                .patronMassAfterExtractionParallelSecond(entity.getPatronMassAfterExtractionParallelSecond())
                .massNaveskiParallelFirst(entity.getMassNaveskiParallelFirst())
                .massNaveskiParallelSecond(entity.getMassNaveskiParallelSecond())
                .dryResidueWeightParallelFirst(entity.getDryResidueWeightParallelFirst())
                .dryResidueWeightParallelSecond(entity.getDryResidueWeightParallelSecond())
                .dryResidueWeightAverage(entity.getDryResidueWeightAverage())
                .build();
    }

    public void updateEntity(@NotNull FatsResearch entity, @NotNull FatsResearchCreateDto dto) {
        entity.setPatronMassBeforeExtractionParallelFirst(dto.patronMassBeforeExtractionParallelFirst());
        entity.setPatronMassBeforeExtractionParallelSecond(dto.patronMassBeforeExtractionParallelSecond());
        entity.setPatronMassAfterExtractionParallelFirst(dto.patronMassAfterExtractionParallelFirst());
        entity.setPatronMassAfterExtractionParallelSecond(dto.patronMassAfterExtractionParallelSecond());
        entity.setMassNaveskiParallelFirst(dto.massNaveskiParallelFirst());
        entity.setMassNaveskiParallelSecond(dto.massNaveskiParallelSecond());
    }
}