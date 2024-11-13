package ru.caloricity.fatsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeService;

@Service
@RequiredArgsConstructor
public class FatsResearchMapper {
    private final ProbeService probeService;

    public FatsResearch toEntity(@NotNull FatsResearchCreateDto dto) {
        return FatsResearch.builder()
                .patronMassBeforeExtractionParallelFirst(dto.patronMassBeforeExtractionParallelFirst())
                .patronMassBeforeExtractionParallelSecond(dto.patronMassBeforeExtractionParallelSecond())
                .patronMassAfterExtractionParallelFirst(dto.patronMassAfterExtractionParallelFirst())
                .patronMassAfterExtractionParallelSecond(dto.patronMassAfterExtractionParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .probe(probeService.getExistingReferenceByIdOrThrow(dto.probeId()))
                .build();
    }

    public FatsResearchDto toDto(@NotNull FatsResearch entity) {
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

    void updateEntity(@NotNull FatsResearch entity, @NotNull FatsResearchUpdateDto dto) {
        entity.setPatronMassBeforeExtractionParallelFirst(dto.patronMassBeforeExtractionParallelFirst());
        entity.setPatronMassBeforeExtractionParallelSecond(dto.patronMassBeforeExtractionParallelSecond());
        entity.setPatronMassAfterExtractionParallelFirst(dto.patronMassAfterExtractionParallelFirst());
        entity.setPatronMassAfterExtractionParallelSecond(dto.patronMassAfterExtractionParallelSecond());
    }
}