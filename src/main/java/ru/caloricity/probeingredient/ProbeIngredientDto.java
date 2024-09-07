package ru.caloricity.probeingredient;

import java.util.Date;
import java.util.UUID;

public record ProbeIngredientDto(UUID id,
                                 Date createdAt,
                                 Date updatedAt,
                                 UUID probeId,
                                 UUID ingredientId,
                                 Float gross,
                                 Float net) {
}
