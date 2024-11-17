package ru.caloricity.carbohydratesresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeService;

@Service
@RequiredArgsConstructor
public class CarbohydratesResearchMapper {
    private final ProbeService probeService;

    public CarbohydratesResearch toEntity(@NotNull CarbohydratesResearchCreateDto dto) {
        return CarbohydratesResearch.builder()
                .byuksaParallelFirst(dto.byuksaParallelFirst())
                .byuksaParallelSecond(dto.byuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond())
                .probe(probeService.getExistingReferenceByIdOrThrow(dto.probeId()))
                .build();
    }

    public CarbohydratesResearchDto toDto(@NotNull CarbohydratesResearch entity) {
        return CarbohydratesResearchDto.builder()
                .id(entity.getId())
                .byuksaParallelFirst(entity.getByuksaParallelFirst())
                .byuksaParallelSecond(entity.getByuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(entity.getByuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(entity.getByuksaAfterDryingParallelSecond())
                .dryResidueWeightParallelFirst(entity.getDryResidueWeightParallelFirst())
                .dryResidueWeightParallelSecond(entity.getDryResidueWeightParallelSecond())
                .dryResidueWeightAverage(entity.getDryResidueWeightAverage())
                .build();
    }

    void updateEntity(@NotNull CarbohydratesResearch entity, @NotNull CarbohydratesResearchUpdateDto dto) {
        entity.setByuksaParallelFirst(dto.byuksaParallelFirst());
        entity.setByuksaParallelSecond(dto.byuksaParallelSecond());
        entity.setByuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst());
        entity.setByuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond());
    }
}