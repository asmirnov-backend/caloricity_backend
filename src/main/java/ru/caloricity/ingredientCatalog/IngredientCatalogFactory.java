package ru.caloricity.ingredientCatalog;

import java.util.UUID;

public class IngredientCatalogFactory {
    public IngredientCatalog createSimple() {
        IngredientCatalog ingredientCatalog = new IngredientCatalog();
        ingredientCatalog.setId(UUID.randomUUID());
        ingredientCatalog.setName("Test name");
        ingredientCatalog.setFats(1);
        ingredientCatalog.setCarbohydrates(1);
        ingredientCatalog.setWater(1);
        ingredientCatalog.setEdiblePart(1);
        ingredientCatalog.setProteins(1);
        return  ingredientCatalog;
    }
}
