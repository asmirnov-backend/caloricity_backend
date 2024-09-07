package ru.caloricity.probeingredient;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Named("ProbeIngredientMapperUtils")
@Component
@RequiredArgsConstructor
public class ProbeIngredientMapperUtils {

    @Named("calcWater")
    Float calcWater(ProbeIngredient probeIngredient) {
        return (probeIngredient.getNet() / probeIngredient.ingredient.getEdiblePart()) * probeIngredient.ingredient.getWater();
    }

    @Named("calcProteins")
    Float calcProteins(ProbeIngredient probeIngredient) {
        return (probeIngredient.getNet() / probeIngredient.ingredient.getEdiblePart()) * probeIngredient.ingredient.getProteins();
    }

    @Named("calcFats")
    Float calcFats(ProbeIngredient probeIngredient) {
        return (probeIngredient.getNet() / probeIngredient.ingredient.getEdiblePart()) * probeIngredient.ingredient.getFats();
    }

    @Named("calcCarbohydrates")
    Float calcCarbohydrates(ProbeIngredient probeIngredient) {
        return (probeIngredient.getNet() / probeIngredient.ingredient.getEdiblePart()) * probeIngredient.ingredient.getCarbohydrates();
    }
}
