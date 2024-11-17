package ru.caloricity.probeingredient;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.ingredient.IngredientService;
import ru.caloricity.probe.ProbeService;

@Service
@RequiredArgsConstructor
public class ProbeIngredientMapper {
    private final ProbeService probeService;
    private final IngredientService ingredientService;

    public ProbeIngredient toEntity(@NotNull ProbeIngredientCreateDto dto) {
        return ProbeIngredient.builder()
                .gross(dto.gross())
                .net(dto.net())
                .probe(probeService.getExistingReferenceByIdOrThrow(dto.probeId()))
                .ingredient(ingredientService.getExistingReferenceByIdOrThrow(dto.ingredientId()))
                .build();
    }

    public ProbeIngredientInPageDto toPageDto(@NotNull ProbeIngredient probeIngredient) {
        Ingredient ingredient = probeIngredient.getIngredient();
        return ProbeIngredientInPageDto.builder()
                .id(probeIngredient.getId())
                .gross(probeIngredient.getGross())
                .net(probeIngredient.getNet())
                .ingredientName(ingredient.getName())
                .drySubstances(probeIngredient.drySubstances())
                .proteins(probeIngredient.proteins())
                .fats(probeIngredient.fats())
                .carbohydrates(probeIngredient.carbohydrates())
                .build();
    }

    void updateEntity(@NotNull ProbeIngredient entity, @NotNull ProbeIngredientUpdateDto dto) {
        entity.setGross(dto.gross());
        entity.setNet(dto.net());
    }
}
