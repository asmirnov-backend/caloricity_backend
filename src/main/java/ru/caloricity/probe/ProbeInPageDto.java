package ru.caloricity.probe;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public record ProbeInPageDto(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt,
                             String name, ProbeType type,
                             String code) {
}
