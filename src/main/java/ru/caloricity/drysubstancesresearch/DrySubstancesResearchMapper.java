package ru.caloricity.drysubstancesresearch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.probe.ProbeService;


@Service
@RequiredArgsConstructor
public class DrySubstancesResearchMapper {
    private final ProbeService probeService;

    public DrySubstancesResearch toEntity(DrySubstancesResearchCreateDto dto) {
        return DrySubstancesResearch.builder()
                .byuksaParallelFirst(dto.byuksaParallelFirst())
                .byuksaParallelSecond(dto.byuksaParallelSecond())
                .byuksaAfterDryingParallelFirst(dto.byuksaAfterDryingParallelFirst())
                .byuksaAfterDryingParallelSecond(dto.byuksaAfterDryingParallelSecond())
                .probe(probeService.getExistingReferenceByIdOrThrow(dto.probeId()))
                .build();
    }

    public DrySubstancesResearchDto toDto(DrySubstancesResearch entity) {
        return DrySubstancesResearchDto.builder()
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
}
