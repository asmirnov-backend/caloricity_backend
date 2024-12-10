package ru.caloricity.probe;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchCreateDto;
import ru.caloricity.probe.research.fatsresearch.FatsResearchCreateDto;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchCreateDto;

@Builder
public record ProbeUpdateDto(
        @NotNull @NotBlank @Size(min = 2) String name,
        @NotNull String code,
        @Nullable @Min(value = 0) Double massTheory,
        @Nullable @Min(value = 0) Double bankaEmptyMass,
        @Nullable @Min(value = 0) Double bankaWithProbeMass,
        @Nullable FatsResearchCreateDto fatsResearch,
        @Nullable DrySubstancesResearchCreateDto drySubstancesResearch,
        @Nullable ProteinsResearchCreateDto proteinsResearch
        ) {
}
