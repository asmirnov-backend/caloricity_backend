package ru.caloricity.common.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String message) {
}
