package ru.caloricity.probe;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProbeDto(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String name,
                       ProbeType type, String code, Float massTheory,
                       Float bankaEmptyMass, Float bankaWithProbeMass) {
}
