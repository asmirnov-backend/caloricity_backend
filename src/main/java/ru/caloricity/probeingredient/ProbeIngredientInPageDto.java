package ru.caloricity.probeingredient;

import lombok.Getter;

import java.util.UUID;

@Getter
public record ProbeIngredientInPageDto(UUID id,
                                       Float gross,
                                       Float net,

                                       String ingredientName,
                                       Float water,
                                       Float proteins,
                                       Float fats,
                                       Float carbohydrates) {
}
