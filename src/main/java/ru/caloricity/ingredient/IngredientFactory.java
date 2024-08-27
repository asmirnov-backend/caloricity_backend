package ru.caloricity.ingredient;

import ru.caloricity.ingredientCatalog.IngredientCatalog;
import ru.caloricity.probe.Probe;

import java.util.UUID;

public class IngredientFactory {
    public Ingredient createSimple() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(UUID.randomUUID());
        ingredient.setGross(1.2f);
        ingredient.setNet(1.5f);
        return ingredient;
    }

    public Ingredient createSimple(IngredientCatalog ingredientCatalog) {
        Ingredient ingredient = createSimple();
        ingredient.setIngredientInCatalog(ingredientCatalog);
        return ingredient;
    }

    public Ingredient createSimple(IngredientCatalog ingredientCatalog, Probe probe) {
        Ingredient ingredient = createSimple();
        ingredient.setIngredientInCatalog(ingredientCatalog);
        ingredient.setProbe(probe);
        return ingredient;
    }
}
