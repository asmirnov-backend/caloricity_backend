package ru.caloricity.probeingredient;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Named("ProbeIngredientMapperUtils")
@Component
@RequiredArgsConstructor
public class ProbeIngredientMapperUtils {

    @Named("calcDrySubstances")
    Float calcDrySubstances(ProbeIngredient probeIngredient) {
        return probeIngredient.getNet() - (probeIngredient.getNet() * probeIngredient.ingredient.getWater() / 100);
    }

    @Named("calcProteins")
    Float calcProteins(ProbeIngredient probeIngredient) {
        return probeIngredient.getNet() * probeIngredient.ingredient.getProteins() / 100;
    }

    @Named("calcFats")
    Float calcFats(ProbeIngredient probeIngredient) {
        return probeIngredient.getNet() * probeIngredient.ingredient.getFats() / 100;
    }

    @Named("calcCarbohydrates")
    Float calcCarbohydrates(ProbeIngredient probeIngredient) {
        return probeIngredient.getNet() * probeIngredient.ingredient.getCarbohydrates() / 100;
    }
}
