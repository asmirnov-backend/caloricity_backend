package ru.caloricity.probeingredient;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ProbeIngredientDto(UUID id,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 UUID probeId,
                                 UUID ingredientId,
                                 Float gross,
                                 Float net
) {
}
