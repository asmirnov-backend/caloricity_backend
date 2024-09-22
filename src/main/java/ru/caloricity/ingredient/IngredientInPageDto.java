package ru.caloricity.ingredient;

import lombok.Getter;

import java.util.UUID;


@Getter
public record IngredientInPageDto(UUID id,

                                  String name,

                                  Float ediblePart,

                                  Float water,

                                  Float proteins,

                                  Float fats,

                                  Float carbohydrates) {

}
