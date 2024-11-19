package ru.caloricity.probe.research.carbohydratesresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeRepository;

@Service
@RequiredArgsConstructor
public class CarbohydratesResearchMapper {
    private final ProbeRepository probeRepository;

    public CarbohydratesResearch toEntity(@NotNull CarbohydratesResearchCreateDto dto) {
        return CarbohydratesResearch.builder()
                .byuksaParallelFirst(dto.byuksaParallelFirst())
                .byuksaParallelSecond(dto.byuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond())
                .probe(probeRepository.getReferenceById(dto.probeId()))
                .build();
    }

    public CarbohydratesResearchDto toDto(CarbohydratesResearch entity) {
        if (entity == null) {
            return null;
        }

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