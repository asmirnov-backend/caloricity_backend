package ru.caloricity.probe;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
public record ProbeDto(UUID id,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt,
                       String name,
                       ProbeType type,
                       String code,
                       Float massTheory,
                       Float bankaEmptyMass,
                       Float bankaWithProbeMass,
                       Float massFact,
                       Double minerals
) {
}
