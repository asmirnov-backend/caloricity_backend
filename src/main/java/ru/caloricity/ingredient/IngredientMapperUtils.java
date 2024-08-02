package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import ru.caloricity.common.exception.EntityNotFoundException;
import ru.caloricity.ingredientCatalog.IngredientCatalog;
import ru.caloricity.ingredientCatalog.IngredientCatalogService;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.ProbeService;

import java.util.UUID;

@Named("IngredientMapperUtils")
@Component
@RequiredArgsConstructor
public class IngredientMapperUtils {
    private final ProbeService probeService;
    private final IngredientCatalogService ingredientCatalogService;

    @Named("mapProbeIdToEntity")
    Probe mapProbeIdToEntity(UUID probeId) {
        return probeService.findById(probeId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("mapIngredientInCatalogIdToEntity")
    IngredientCatalog mapIngredientInCatalogIdToEntity(UUID ingredientInCatalogId) {
        return ingredientCatalogService.findById(ingredientInCatalogId).orElseThrow(EntityNotFoundException::new);
    }

    @Named("calcWater")
    Float calcWater(Ingredient ingredient) {
        return (ingredient.getNet() /  ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getWater();
    }

    @Named("calcProteins")
    Float calcProteins(Ingredient ingredient) {
        return (ingredient.getNet() /  ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getProteins();
    }

    @Named("calcFats")
    Float calcFats(Ingredient ingredient) {
        return (ingredient.getNet() /  ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getFats();
    }

    @Named("calcCarbohydrates")
    Float calcCarbohydrates(Ingredient ingredient) {
        return (ingredient.getNet() /  ingredient.getIngredientInCatalog().getEdiblePart()) * ingredient.getIngredientInCatalog().getCarbohydrates();
    }
}