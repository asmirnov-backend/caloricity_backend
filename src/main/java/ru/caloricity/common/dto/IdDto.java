package ru.caloricity.common.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public record IdDto(UUID id) {
}
