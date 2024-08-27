package ru.caloricity.proteinsresearch;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProteinsResearchUpdateDto {
    @NotNull
    private Float titrantVolume;

    @NotNull
    private Float mass;

    @NotNull
    private Float controlVolume;

    @NotNull
    private Float coefficient;
}
