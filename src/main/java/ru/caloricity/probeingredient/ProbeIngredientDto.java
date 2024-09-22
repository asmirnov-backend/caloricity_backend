package ru.caloricity.probeingredient;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public record ProbeIngredientDto(UUID id,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 UUID probeId,
                                 UUID ingredientId,
                                 Float gross,
                                 Float net) {
}
