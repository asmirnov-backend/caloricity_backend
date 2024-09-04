package ru.caloricity.ingredientcatalog;

import ru.caloricity.common.DefinedUuidIterator;

import java.util.Iterator;
import java.util.UUID;

public class IngredientCatalogFactory {
    Iterator<UUID> uuidIterator = new DefinedUuidIterator();

    public IngredientCatalog createSimple() {
        IngredientCatalog ingredientCatalog = new IngredientCatalog();
        ingredientCatalog.setId(uuidIterator.next());
        ingredientCatalog.setName("Test name");
        ingredientCatalog.setFats(1f);
        ingredientCatalog.setCarbohydrates(1f);
        ingredientCatalog.setWater(1f);
        ingredientCatalog.setEdiblePart(1f);
        ingredientCatalog.setProteins(1f);
        return ingredientCatalog;
    }
}
