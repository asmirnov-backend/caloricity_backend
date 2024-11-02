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
                .dryResidueWeightParallelFirst(entity.getDryResidueWeightParallelFirst())
                .dryResidueWeightParallelSecond(entity.getDryResidueWeightParallelSecond())
                .dryResidueWeightAverage(entity.getDryResidueWeightAverage())
                .build();
    }
}