package ru.caloricity.probe;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProbeUpdateDto(
        @NotNull @NotBlank @Size(min = 2) String name,
        @NotNull String code,
        @Nullable @Min(value = 0) Float massTheory,
        @NotNull @Min(value = 0) Float massFact
) {
}