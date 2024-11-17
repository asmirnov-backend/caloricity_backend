package ru.caloricity.common.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record IdDto(UUID id) {
}
