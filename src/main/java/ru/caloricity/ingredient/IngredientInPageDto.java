package ru.caloricity.ingredient;

import java.util.UUID;

public record IngredientInPageDto(UUID id, Float gross, Float net,
                                  String name, Float water, Float proteins,
                                  Float fats, Float carbohydrates) {}
