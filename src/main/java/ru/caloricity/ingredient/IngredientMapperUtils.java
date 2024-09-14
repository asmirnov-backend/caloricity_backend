package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Named("IngredientMapperUtils")
@Component
@RequiredArgsConstructor
public class IngredientMapperUtils {
    private final IngredientService ingredientService;

    @Named("getExistingReferenceByIdOrThrow")
    public Ingredient getExistingReferenceByIdOrThrow(UUID probeId) {
        return ingredientService.getExistingReferenceByIdOrThrow(probeId);
    }
}
