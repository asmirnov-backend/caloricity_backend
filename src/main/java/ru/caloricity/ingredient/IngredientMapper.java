package ru.caloricity.ingredient;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientMapper {
    public Ingredient toEntity(@NotNull IngredientCreateDto dto) {
        return Ingredient.builder()
                .name(dto.name())
                .ediblePart(dto.ediblePart())
                .water(dto.water())
                .proteins(dto.proteins())
                .fats(dto.fats())
                .carbohydrates(dto.carbohydrates())
                .build();
    }
}
