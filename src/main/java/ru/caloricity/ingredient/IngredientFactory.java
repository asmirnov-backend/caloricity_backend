package ru.caloricity.ingredient;

import ru.caloricity.common.DefinedUuidIterator;

import java.util.Iterator;
import java.util.UUID;

public class IngredientFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public Ingredient createSimple() {
        return createSimpleWithName("Test name");
    }

    public Ingredient createSimpleWithName(String name) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(uuidIterator.next());
        ingredient.setName(name);
        ingredient.setFats(1f);
        ingredient.setCarbohydrates(2f);
        ingredient.setWater(3f);
        ingredient.setEdiblePart(4f);
        ingredient.setProteins(5f);
        return ingredient;
    }
}
