package ru.caloricity.probe;

import lombok.Builder;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchDto;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
public record ProbeDto(UUID id,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       String name,
                       ProbeType type,
                       String code,
                       Double massTheory,
                       Double bankaEmptyMass,
                       Double bankaWithProbeMass,
                       Double massFact,
                       Double minerals,
                       Double theoreticalCaloricity,

                       DrySubstancesResearchDto drySubstancesResearch,
                       FatsResearchDto fatsResearch,
                       ProteinsResearchDto proteinsResearch) {
}
