package ru.caloricity.common.dto;

import lombok.Getter;

@Getter
public record ErrorResponseDto(String message) {
}
