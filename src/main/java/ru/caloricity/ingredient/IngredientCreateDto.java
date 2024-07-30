package ru.caloricity.ingredient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCreateDto {
    @NotNull
    @Min(0)
    private float gross;

    @NotNull
    @Min(0)
    private float net;
}
