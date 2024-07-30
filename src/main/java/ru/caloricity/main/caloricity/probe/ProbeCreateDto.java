package ru.caloricity.main.caloricity.probe;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProbeCreateDto {
    @NotNull
    @NotBlank
    @Size(min=2)
    private String name;

    @NotNull
    private ProbeType type;

    @NotNull
    private String code;

    @NotNull
    @Min(value = 0)
    private float massTheory;

    @NotNull
    @Min(value = 0)
    private float massFact;
}
