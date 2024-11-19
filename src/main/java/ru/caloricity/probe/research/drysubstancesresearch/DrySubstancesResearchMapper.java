package ru.caloricity.probe.research.drysubstancesresearch;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeRepository;


@Service
@RequiredArgsConstructor
public class DrySubstancesResearchMapper {
    private final ProbeRepository probeRepository;

    public DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto) {
        return DrySubstancesResearch.builder()
                .byuksaParallelFirst(dto.byuksaParallelFirst())
                .byuksaParallelSecond(dto.byuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond())
                .massNaveskiParallelFirst(dto.massNaveskiParallelFirst())
                .massNaveskiParallelSecond(dto.massNaveskiParallelSecond())
                .probe(probeRepository.getReferenceById(dto.probeId()))
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

    void updateEntity(@NotNull DrySubstancesResearch entity, @NotNull DrySubstancesResearchUpdateDto dto) {
        entity.setByuksaParallelFirst(dto.byuksaParallelFirst());
        entity.setByuksaParallelSecond(dto.byuksaParallelSecond());
        entity.setByuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst());
        entity.setByuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond());
        entity.setMassNaveskiParallelFirst(dto.massNaveskiParallelFirst());
        entity.setMassNaveskiParallelSecond(dto.massNaveskiParallelSecond());
    }
}
