package ru.caloricity.ingredient;

import java.util.UUID;

public class IngredientFactory {
    public Ingredient createSimple() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(UUID.randomUUID());
        ingredient.setGross(1.2f);
        ingredient.setNet(1.5f);
        return ingredient;
    }
}
