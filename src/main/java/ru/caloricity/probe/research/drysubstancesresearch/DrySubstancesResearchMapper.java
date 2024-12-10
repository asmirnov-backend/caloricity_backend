package ru.caloricity.probe.research.drysubstancesresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DrySubstancesResearchMapper {
    public DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto) {
        if (dto == null) {
            return null;
        }

        return DrySubstancesResearch.builder()
                .byuksaParallelFirst(dto.byuksaParallelFirst())
                .byuksaParallelSecond(dto.byuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .build();
    }

    public DrySubstancesResearchDto toDto(DrySubstancesResearch entity) {
        if (entity == null) {
            return null;
        }

        return DrySubstancesResearchDto.builder()
                .id(entity.getId())
                .byuksaParallelFirst(entity.getByuksaParallelFirst())
                .byuksaParallelSecond(entity.getByuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(entity.getByuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(entity.getByuksaAfterDryingParallelSecond())
                .massNaveskiParallelFirst(entity.getMassNaveskiParallelFirst())
                .massNaveskiParallelSecond(entity.getMassNaveskiParallelSecond())
                .dryResidueWeightParallelFirst(entity.getDryResidueWeightParallelFirst())
                .dryResidueWeightParallelSecond(entity.getDryResidueWeightParallelSecond())
                .dryResidueWeightAverage(entity.getDryResidueWeightAverage())
                .build();
    }

    public void updateEntity(@NotNull DrySubstancesResearch entity, @NotNull DrySubstancesResearchCreateDto dto) {
        entity.setByuksaParallelFirst(dto.byuksaParallelFirst());
        entity.setByuksaParallelSecond(dto.byuksaParallelSecond());
        entity.setByuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst());
        entity.setByuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond());
        entity.setMassNaveskiParallelFirst(dto.massNaveskiParallelFirst());
        entity.setMassNaveskiParallelSecond(dto.massNaveskiParallelSecond());
    }
}
