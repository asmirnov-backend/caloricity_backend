package ru.caloricity.ingredientCatalog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientCatalogCreateDto {
    @NotNull
    @NotBlank
    @Size(min=2)
    private String name;

    @NotNull
    @Min(value = 0)
    private Float ediblePart;

    @NotNull
    @Min(value = 0)
    private Float water;

    @NotNull
    @Min(value = 0)
    private Float proteins;

    @NotNull
    @Min(value = 0)
    private Float fats;

    @NotNull
    @Min(value = 0)
    private Float carbohydrates;
}
