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
        ingredient.setFats(1.);
        ingredient.setCarbohydrates(2.);
        ingredient.setWater(3.);
        ingredient.setEdiblePart(4.);
        ingredient.setProteins(5.);
        return ingredient;
    }
}
