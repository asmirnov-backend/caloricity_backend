package ru.caloricity.ingredientCatalog;

import java.util.UUID;

public class IngredientCatalogFactory {
    public IngredientCatalog createSimple() {
        IngredientCatalog ingredientCatalog = new IngredientCatalog();
        ingredientCatalog.setId(UUID.randomUUID());
        ingredientCatalog.setName("Test name");
        ingredientCatalog.setFats(1f);
        ingredientCatalog.setCarbohydrates(1f);
        ingredientCatalog.setWater(1f);
        ingredientCatalog.setEdiblePart(1f);
        ingredientCatalog.setProteins(1f);
        return ingredientCatalog;
    }
}
