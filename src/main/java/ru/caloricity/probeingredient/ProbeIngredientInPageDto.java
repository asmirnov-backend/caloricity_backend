package ru.caloricity.probeingredient;

import java.util.UUID;

public record ProbeIngredientInPageDto(UUID id,
                                       Float gross,
                                       Float net,

                                       String ingredientName,
                                       Float water,
                                       Float proteins,
                                       Float fats,
                                       Float carbohydrates) {
}
