package ru.caloricity.ingredient;

import ru.caloricity.common.DefinedUuidIterator;

import java.util.Iterator;
import java.util.UUID;

public class IngredientFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public Ingredient createSimple() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(uuidIterator.next());
        ingredient.setName("Test name");
        ingredient.setFats(1f);
        ingredient.setCarbohydrates(1f);
        ingredient.setWater(1f);
        ingredient.setEdiblePart(1f);
        ingredient.setProteins(1f);
        return ingredient;
    }
}
