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
                       Double massTheory,
                       Double bankaEmptyMass,
                       Double bankaWithProbeMass,
                       Double massFact,
                       Double minerals
) {
}
