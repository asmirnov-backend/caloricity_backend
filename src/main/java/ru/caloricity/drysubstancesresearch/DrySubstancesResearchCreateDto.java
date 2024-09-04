package ru.caloricity.drysubstancesresearch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DrySubstancesResearchCreateDto {
    @NotNull
    @Min(0)
    private Float byuksaParallelFirst;

    @NotNull
    @Min(0)
    private Float byuksaParallelSecond;

    @NotNull
    @Min(0)
    private Float byuksaAfterDryingParallelFirst;

    @NotNull
    @Min(0)
    private Float byuksaAfterDryingParallelSecond;

    @NotNull
    private UUID probeId;
}
