package ru.caloricity.probeingredient;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProbeIngredientDto(UUID id,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 UUID probeId,
                                 UUID ingredientId,
                                 Float gross,
                                 Float net) {
}
