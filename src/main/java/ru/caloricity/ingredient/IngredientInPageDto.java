package ru.caloricity.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class IngredientInPageDto {
    private UUID id;
    private float gross;
    private float net;
}
