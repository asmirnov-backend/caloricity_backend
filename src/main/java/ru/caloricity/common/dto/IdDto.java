package ru.caloricity.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IdDto {
    private UUID id;
}