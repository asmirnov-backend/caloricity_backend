package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.caloricity.common.exception.EntityNotFoundException;
import ru.caloricity.ingredientcatalog.IngredientCatalog;
import ru.caloricity.ingredientcatalog.IngredientCatalogService;

import java.util.UUID;

@Named("IngredientMapperUtils")
@Component
@RequiredArgsConstructor
public class IngredientMapperUtils {
    private final IngredientCatalogService ingredientCatalogService;

    @Named("mapIngredientInCatalogIdToEntity")
    IngredientCatalog mapIngredientInCatalogIdToEntity(UUID ingredientInCatalogId) {
        return ingredientCatalogService.findById(ingredientInCatalogId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("calcWater")
    Float calcWater(Ingredient ingredient) {
        return (ingredient.getNet() / ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getWater();
    }

    @Named("calcProteins")
    Float calcProteins(Ingredient ingredient) {
        return (ingredient.getNet() / ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getProteins();
    }

    @Named("calcFats")
    Float calcFats(Ingredient ingredient) {
        return (ingredient.getNet() / ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getFats();
    }

    @Named("calcCarbohydrates")
    Float calcCarbohydrates(Ingredient ingredient) {
        return (ingredient.getNet() / ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getCarbohydrates();
    }
}
