package ru.caloricity.probeingredient;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProbeIngredientInPageDto(UUID id,
                                       Double gross,
                                       Double net,
                                       String ingredientName,
                                       Double drySubstances,
                                       Double proteins,
                                       Double fats,
                                       Double carbohydrates
) {
}
