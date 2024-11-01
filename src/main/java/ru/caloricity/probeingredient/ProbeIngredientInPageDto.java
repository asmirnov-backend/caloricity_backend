package ru.caloricity.probeingredient;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProbeIngredientInPageDto(UUID id,
                                       Float gross,
                                       Float net,
                                       String ingredientName,
                                       Float drySubstances,
                                       Float proteins,
                                       Float fats,
                                       Float carbohydrates
) {
}
